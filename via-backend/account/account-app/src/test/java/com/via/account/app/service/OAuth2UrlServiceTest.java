package com.via.account.app.service;

import com.via.account.app.dto.OAuth2LoginUrl;
import com.via.account.app.external.OAuth2LoginUriProvider;
import com.via.account.app.repository.OAuth2ContextRepository;
import com.via.account.domain.enums.OAuth2Provider;
import com.via.account.domain.model.OAuth2Context;
import com.via.account.domain.service.OAuth2ContextService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URI;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@Tag("UNIT_TEST")
@ExtendWith(MockitoExtension.class)
@DisplayName("OAuth2UrlService 단위 테스트")
class OAuth2UrlServiceTest {

    @InjectMocks
    private OAuth2UrlService oAuth2UrlService;

    @Mock
    private OAuth2LoginUriProvider oAuth2LoginUriProvider;

    @Mock
    private OAuth2ContextService oAuth2ContextService;

    @Mock
    private OAuth2ContextRepository oAuth2ContextRepository;

    @Nested
    @DisplayName("generateLoginUrl 메서드는")
    class Describe_generateLoginUrl {

        private final OAuth2Provider provider = OAuth2Provider.GOOGLE;
        private final String codeVerifier = "test-code-verifier";
        private final String redirectPath = "/dashboard";
        private final String redirectUri = "http://localhost:3000/callback";

        @Test
        @DisplayName("OAuth2 로그인 URL을 생성한다")
        void it_generates_oauth2_login_url() {
            // given
            String state = "test-state";
            OAuth2Context context = createOAuth2Context(state);
            URI loginUri = URI.create("https://accounts.google.com/o/oauth2/v2/auth?state=" + state);

            given(oAuth2ContextService.createContext(eq(provider), eq(codeVerifier), eq(redirectPath), eq(redirectUri)))
                    .willReturn(context);
            given(oAuth2ContextRepository.save(context)).willReturn(context);
            given(oAuth2LoginUriProvider.create(eq(provider), eq(redirectUri), eq(state), any(String.class)))
                    .willReturn(loginUri);

            // when
            OAuth2LoginUrl result = oAuth2UrlService.generateLoginUrl(provider, codeVerifier, redirectPath, redirectUri);

            // then
            assertThat(result).isNotNull();
            assertThat(result.url()).isEqualTo(loginUri.toString());
            assertThat(result.state()).isEqualTo(state);
            assertThat(result.provider()).isEqualTo(provider);
        }

        @Test
        @DisplayName("OAuth2Context를 생성하고 저장한다")
        void it_creates_and_saves_oauth2_context() {
            // given
            String state = "test-state";
            LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(5);
            OAuth2Context context = createOAuth2Context(state);
            URI loginUri = URI.create("https://accounts.google.com/o/oauth2/v2/auth");

            given(oAuth2ContextService.createContext(provider, codeVerifier, redirectPath, redirectUri))
                    .willReturn(context);
            given(oAuth2ContextRepository.save(context)).willReturn(context);
            given(oAuth2LoginUriProvider.create(eq(provider), eq(redirectUri), eq(state), any(String.class)))
                    .willReturn(loginUri);

            // when
            oAuth2UrlService.generateLoginUrl(provider, codeVerifier, redirectPath, redirectUri);

            // then
            verify(oAuth2ContextService).createContext(provider, codeVerifier, redirectPath, redirectUri);
            verify(oAuth2ContextRepository).save(context);
        }

        @Test
        @DisplayName("CodeChallenge를 생성하여 URI Provider에 전달한다")
        void it_creates_code_challenge_and_passes_to_uri_provider() {
            // given
            String state = "test-state";
            LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(5);
            OAuth2Context context = createOAuth2Context(state);
            URI loginUri = URI.create("https://accounts.google.com/o/oauth2/v2/auth");

            given(oAuth2ContextService.createContext(provider, codeVerifier, redirectPath, redirectUri))
                    .willReturn(context);
            given(oAuth2ContextRepository.save(context)).willReturn(context);
            given(oAuth2LoginUriProvider.create(eq(provider), eq(redirectUri), eq(state), any(String.class)))
                    .willReturn(loginUri);

            // when
            oAuth2UrlService.generateLoginUrl(provider, codeVerifier, redirectPath, redirectUri);

            // then
            ArgumentCaptor<String> codeChallengeCaptor = ArgumentCaptor.forClass(String.class);
            verify(oAuth2LoginUriProvider).create(
                    eq(provider),
                    eq(redirectUri),
                    eq(state),
                    codeChallengeCaptor.capture()
            );

            String codeChallenge = codeChallengeCaptor.getValue();
            assertThat(codeChallenge).isNotNull();
            assertThat(codeChallenge).isNotEmpty();
        }

        @Test
        @DisplayName("생성된 URL에 state가 포함된다")
        void it_includes_state_in_generated_url() {
            // given
            String state = "unique-state-value";
            LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(5);
            OAuth2Context context = createOAuth2Context(state);
            URI loginUri = URI.create("https://accounts.google.com/o/oauth2/v2/auth?state=" + state);

            given(oAuth2ContextService.createContext(provider, codeVerifier, redirectPath, redirectUri))
                    .willReturn(context);
            given(oAuth2ContextRepository.save(context)).willReturn(context);
            given(oAuth2LoginUriProvider.create(eq(provider), eq(redirectUri), eq(state), any(String.class)))
                    .willReturn(loginUri);

            // when
            OAuth2LoginUrl result = oAuth2UrlService.generateLoginUrl(provider, codeVerifier, redirectPath, redirectUri);

            // then
            assertThat(result.url()).contains(state);
            assertThat(result.state()).isEqualTo(state);
        }

        @Test
        @DisplayName("여러 OAuth2 Provider에 대해 동작한다")
        void it_works_for_multiple_providers() {
            // given
            OAuth2Provider kakaoProvider = OAuth2Provider.KAKAO;
            String state = "test-state";
            LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(5);
            OAuth2Context context = createOAuth2Context(state);
            URI loginUri = URI.create("https://kauth.kakao.com/oauth/authorize");

            given(oAuth2ContextService.createContext(kakaoProvider, codeVerifier, redirectPath, redirectUri))
                    .willReturn(context);
            given(oAuth2ContextRepository.save(context)).willReturn(context);
            given(oAuth2LoginUriProvider.create(eq(kakaoProvider), eq(redirectUri), eq(state), any(String.class)))
                    .willReturn(loginUri);

            // when
            OAuth2LoginUrl result = oAuth2UrlService.generateLoginUrl(kakaoProvider, codeVerifier, redirectPath, redirectUri);

            // then
            assertThat(result).isNotNull();
            assertThat(result.provider()).isEqualTo(kakaoProvider);
            verify(oAuth2ContextService).createContext(kakaoProvider, codeVerifier, redirectPath, redirectUri);
        }

        @Test
        @DisplayName("만료 시간이 정확하게 설정된다")
        void it_sets_expiration_time_correctly() {
            // given
            String state = "test-state";
            OAuth2Context context = createOAuth2Context(state);
            URI loginUri = URI.create("https://accounts.google.com/o/oauth2/v2/auth");

            given(oAuth2ContextService.createContext(provider, codeVerifier, redirectPath, redirectUri))
                    .willReturn(context);
            given(oAuth2ContextRepository.save(context)).willReturn(context);
            given(oAuth2LoginUriProvider.create(eq(provider), eq(redirectUri), eq(state), any(String.class)))
                    .willReturn(loginUri);

            // when
            OAuth2LoginUrl result = oAuth2UrlService.generateLoginUrl(provider, codeVerifier, redirectPath, redirectUri);

            // then
            assertThat(result.expiresAt()).isEqualTo(context.getExpiresAt());
        }

        private OAuth2Context createOAuth2Context(String state) {
            return OAuth2Context.builder()
                    .state(state)
                    .codeVerifier(codeVerifier)
                    .redirectUri(redirectUri)
                    .redirectPath(redirectPath)
                    .build();
        }
    }
}
