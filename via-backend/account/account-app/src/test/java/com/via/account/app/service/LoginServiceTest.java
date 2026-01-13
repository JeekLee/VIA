package com.via.account.app.service;

import com.via.account.app.dto.LoginContext;
import com.via.account.app.enums.LoginStatus;
import com.via.account.app.external.OAuth2TokenClient;
import com.via.account.app.external.OAuth2UserProfileClient;
import com.via.account.app.repository.MemberRepository;
import com.via.account.app.repository.OAuth2AccountRepository;
import com.via.account.app.repository.OAuth2ContextRepository;
import com.via.account.app.repository.RefreshTokenRepository;
import com.via.account.domain.enums.MemberAuthority;
import com.via.account.domain.enums.OAuth2Provider;
import com.via.account.domain.model.*;
import com.via.account.domain.model.id.MemberId;
import com.via.account.domain.model.vo.MemberImage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@Tag("UNIT_TEST")
@ExtendWith(MockitoExtension.class)
@DisplayName("LoginService 단위 테스트")
class LoginServiceTest {

    @InjectMocks
    private LoginService loginService;

    @Mock
    private OAuth2TokenClient oAuth2TokenClient;

    @Mock
    private OAuth2UserProfileClient oAuth2UserProfileClient;

    @Mock
    private MemberManager memberManager;

    @Mock
    private LoginValidator loginValidator;

    @Mock
    private OAuth2AccountRepository oAuth2AccountRepository;

    @Mock
    private OAuth2ContextRepository oAuth2ContextRepository;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private MemberRepository memberRepository;

    @Nested
    @DisplayName("login 메서드는")
    class Describe_login {

        private final OAuth2Provider provider = OAuth2Provider.GOOGLE;
        private final String code = "auth-code";
        private final String state = "state-value";
        private final String codeVerifier = "code-verifier";
        private final String redirectUri = "http://localhost:3000/callback";
        private final String redirectPath = "/dashboard";

        @Nested
        @DisplayName("OAuth2Context가 존재하지 않으면")
        class Context_oauth2_context_not_found {

            @Test
            @DisplayName("예외를 발생시킨다")
            void it_throws_exception() {
                // given
                given(oAuth2ContextRepository.findByState(state))
                        .willReturn(Optional.empty());

                // when & then
                assertThatThrownBy(() -> loginService.login(provider, code, state))
                        .isInstanceOf(RuntimeException.class);

                verify(oAuth2ContextRepository).findByState(state);
                verifyNoInteractions(oAuth2TokenClient, oAuth2UserProfileClient);
            }
        }

        @Nested
        @DisplayName("신규 회원이 로그인하면")
        class Context_new_member_login {

            @Test
            @DisplayName("새 회원을 생성하고 BLOCKED_NEW_MEMBER 상태를 반환한다")
            void it_creates_new_member_and_returns_blocked_status() {
                // given
                OAuth2Context oAuth2Context = createOAuth2Context();
                OAuth2AccessToken accessToken = createAccessToken();
                OAuth2UserInfo userInfo = createUserInfo("new@example.com");
                Member newMember = createMember("new@example.com");

                given(oAuth2ContextRepository.findByState(state))
                        .willReturn(Optional.of(oAuth2Context));
                given(oAuth2TokenClient.getAccessToken(provider, code, codeVerifier, redirectUri))
                        .willReturn(accessToken);
                given(oAuth2UserProfileClient.getUserProfile(provider, accessToken.getAccessToken()))
                        .willReturn(userInfo);
                given(oAuth2AccountRepository.findByOAuth2ProviderAndEmail(provider, userInfo.getEmail()))
                        .willReturn(Optional.empty());
                given(memberManager.createAndSave(userInfo.getEmail(), userInfo.getImageUrl()))
                        .willReturn(newMember);

                // when
                LoginContext result = loginService.login(provider, code, state);

                // then
                assertThat(result).isNotNull();
                Assertions.assertThat(result.status()).isEqualTo(LoginStatus.BLOCKED_NEW_MEMBER);
                assertThat(result.memberId()).isEqualTo(newMember.memberId());
                assertThat(result.email()).isEqualTo("new@example.com");
                assertThat(result.redirectPath()).isEqualTo(redirectPath);

                verify(memberManager).createAndSave(userInfo.getEmail(), userInfo.getImageUrl());
                verify(oAuth2AccountRepository).save(any(OAuth2Account.class));
            }
        }

        @Nested
        @DisplayName("기존 회원이 로그인하면")
        class Context_existing_member_login {

            @Test
            @DisplayName("회원 정보와 로그인 상태를 반환한다")
            void it_returns_member_info_and_status() {
                // given
                String email = "existing@example.com";
                OAuth2Context oAuth2Context = createOAuth2Context();
                OAuth2AccessToken accessToken = createAccessToken();
                OAuth2UserInfo userInfo = createUserInfo(email);
                Member existingMember = createMember(email);
                OAuth2Account oAuth2Account = createOAuth2Account(existingMember.memberId(), provider, email);

                given(oAuth2ContextRepository.findByState(state))
                        .willReturn(Optional.of(oAuth2Context));
                given(oAuth2TokenClient.getAccessToken(provider, code, codeVerifier, redirectUri))
                        .willReturn(accessToken);
                given(oAuth2UserProfileClient.getUserProfile(provider, accessToken.getAccessToken()))
                        .willReturn(userInfo);
                given(oAuth2AccountRepository.findByOAuth2ProviderAndEmail(provider, email))
                        .willReturn(Optional.of(oAuth2Account));
                given(memberRepository.findById(existingMember.memberId()))
                        .willReturn(Optional.of(existingMember));
                given(loginValidator.get(existingMember))
                        .willReturn(LoginStatus.ALLOWED);

                // when
                LoginContext result = loginService.login(provider, code, state);

                // then
                assertThat(result).isNotNull();
                Assertions.assertThat(result.status()).isEqualTo(LoginStatus.ALLOWED);
                assertThat(result.memberId()).isEqualTo(existingMember.memberId());
                assertThat(result.email()).isEqualTo(email);
                assertThat(result.redirectPath()).isEqualTo(redirectPath);

                verify(loginValidator).get(existingMember);
                verify(memberManager, never()).createAndSave(any(), any());
            }
        }

        @Nested
        @DisplayName("OAuth2Account는 존재하지만 Member가 없으면")
        class Context_oauth2_account_exists_but_member_not_found {

            @Test
            @DisplayName("예외를 발생시킨다")
            void it_throws_exception() {
                // given
                String email = "orphan@example.com";
                OAuth2Context oAuth2Context = createOAuth2Context();
                OAuth2AccessToken accessToken = createAccessToken();
                OAuth2UserInfo userInfo = createUserInfo(email);
                MemberId memberId = new MemberId(1L);
                OAuth2Account oAuth2Account = createOAuth2Account(memberId, provider, email);

                given(oAuth2ContextRepository.findByState(state))
                        .willReturn(Optional.of(oAuth2Context));
                given(oAuth2TokenClient.getAccessToken(provider, code, codeVerifier, redirectUri))
                        .willReturn(accessToken);
                given(oAuth2UserProfileClient.getUserProfile(provider, accessToken.getAccessToken()))
                        .willReturn(userInfo);
                given(oAuth2AccountRepository.findByOAuth2ProviderAndEmail(provider, email))
                        .willReturn(Optional.of(oAuth2Account));
                given(memberRepository.findById(memberId))
                        .willReturn(Optional.empty());

                // when & then
                assertThatThrownBy(() -> loginService.login(provider, code, state))
                        .isInstanceOf(RuntimeException.class);

                verify(memberRepository).findById(memberId);
            }
        }

        private OAuth2Context createOAuth2Context() {
            return OAuth2Context.builder()
                    .state(state)
                    .codeVerifier(codeVerifier)
                    .redirectUri(redirectUri)
                    .redirectPath(redirectPath)
                    .build();
        }

        private OAuth2AccessToken createAccessToken() {
            OAuth2AccessToken accessToken = mock(OAuth2AccessToken.class);
            given(accessToken.getAccessToken()).willReturn("access-token");
            return accessToken;
        }

        private OAuth2UserInfo createUserInfo(String email) {
            OAuth2UserInfo userInfo = mock(OAuth2UserInfo.class);
            given(userInfo.getEmail()).willReturn(email);
            return userInfo;
        }
    }

    @Nested
    @DisplayName("logOut 메서드는")
    class Describe_logOut {

        @Test
        @DisplayName("리프레시 토큰을 삭제한다")
        void it_deletes_refresh_token() {
            // given
            String token = "refresh-token";

            // when
            loginService.logOut(token);

            // then
            verify(refreshTokenRepository).deleteByToken(token);
        }
    }

    @Nested
    @DisplayName("getContextByToken 메서드는")
    class Describe_getContextByToken {

        @Nested
        @DisplayName("유효한 토큰이 주어지면")
        class Context_valid_token {

            @Test
            @DisplayName("회원의 LoginContext를 반환한다")
            void it_returns_login_context() {
                // given
                String token = "valid-token";
                Member member = createMember("user@example.com");
                RefreshToken refreshToken = createRefreshToken(token, member.memberId());

                given(refreshTokenRepository.findByToken(token))
                        .willReturn(Optional.of(refreshToken));
                given(memberRepository.findById(member.memberId()))
                        .willReturn(Optional.of(member));
                given(loginValidator.get(member))
                        .willReturn(LoginStatus.ALLOWED);

                // when
                LoginContext result = loginService.getContextByToken(token);

                // then
                assertThat(result).isNotNull();
                assertThat(result.memberId()).isEqualTo(member.memberId());
                assertThat(result.email()).isEqualTo("user@example.com");
                Assertions.assertThat(result.status()).isEqualTo(LoginStatus.ALLOWED);
                assertThat(result.redirectPath()).isNull();

                verify(refreshTokenRepository).findByToken(token);
                verify(memberRepository).findById(member.memberId());
                verify(loginValidator).get(member);
            }
        }

        @Nested
        @DisplayName("존재하지 않는 토큰이 주어지면")
        class Context_token_not_found {

            @Test
            @DisplayName("예외를 발생시킨다")
            void it_throws_exception() {
                // given
                String token = "invalid-token";
                given(refreshTokenRepository.findByToken(token))
                        .willReturn(Optional.empty());

                // when & then
                assertThatThrownBy(() -> loginService.getContextByToken(token))
                        .isInstanceOf(RuntimeException.class);

                verify(refreshTokenRepository).findByToken(token);
                verifyNoInteractions(memberRepository, loginValidator);
            }
        }

        @Nested
        @DisplayName("토큰은 존재하지만 회원이 없으면")
        class Context_member_not_found {

            @Test
            @DisplayName("예외를 발생시킨다")
            void it_throws_exception() {
                // given
                String token = "orphan-token";
                MemberId memberId = new MemberId(1L);
                RefreshToken refreshToken = createRefreshToken(token, memberId);

                given(refreshTokenRepository.findByToken(token))
                        .willReturn(Optional.of(refreshToken));
                given(memberRepository.findById(memberId))
                        .willReturn(Optional.empty());

                // when & then
                assertThatThrownBy(() -> loginService.getContextByToken(token))
                        .isInstanceOf(RuntimeException.class);

                verify(memberRepository).findById(memberId);
                verifyNoInteractions(loginValidator);
            }
        }

        private RefreshToken createRefreshToken(String token, MemberId memberId) {
            return RefreshToken.builder()
                    .token(token)
                    .memberId(memberId)
                    .build();
        }
    }

    private Member createMember(String email) {
        return Member.builder()
                .memberId(new MemberId(1L))
                .nickname("TestUser")
                .email(email)
                .image(new MemberImage("/test/user"))
                .authorities(List.of(MemberAuthority.USER))
                .build();
    }

    private OAuth2Account createOAuth2Account(MemberId memberId, OAuth2Provider provider, String email) {
        return OAuth2Account.builder()
                .memberId(memberId)
                .provider(provider)
                .email(email)
                .build();
    }
}