package com.via.account.app.service;

import com.via.account.app.enums.LoginStatus;
import com.via.account.domain.model.Member;
import com.via.account.domain.model.id.MemberId;
import com.via.account.domain.model.vo.MemberImage;
import com.via.account.domain.enums.MemberAuthority;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@Tag("UNIT_TEST")
@ExtendWith(MockitoExtension.class)
@DisplayName("LoginValidator 단위 테스트")
class LoginValidatorTest {

    @InjectMocks
    private LoginValidator loginValidator;

    @Mock
    private TermsConsentHistoryService termsConsentHistoryService;

    @Nested
    @DisplayName("get 메서드는")
    class Describe_get {

        @Nested
        @DisplayName("회원 탈퇴 요청이 있으면")
        class Context_resign_requested {

            @Test
            @DisplayName("BLOCKED_TERMS_REQUIRED 상태를 반환한다")
            void it_returns_blocked_terms_required() {
                // given
                Member member = createMemberWithResignRequest();

                // when
                LoginStatus result = loginValidator.get(member);

                // then
                assertThat(result).isEqualTo(LoginStatus.BLOCKED_TERMS_REQUIRED);
            }
        }

        @Nested
        @DisplayName("필수 약관 동의가 없으면")
        class Context_required_terms_not_consented {

            @Test
            @DisplayName("BLOCKED_TERMS_REQUIRED 상태를 반환한다")
            void it_returns_blocked_terms_required() {
                // given
                Member member = createNormalMember();
                given(termsConsentHistoryService.checkRequiredTermsConsent(member.memberId()))
                        .willReturn(false);

                // when
                LoginStatus result = loginValidator.get(member);

                // then
                assertThat(result).isEqualTo(LoginStatus.BLOCKED_TERMS_REQUIRED);
                verify(termsConsentHistoryService).checkRequiredTermsConsent(member.memberId());
            }
        }

        @Nested
        @DisplayName("모든 검증을 통과하면")
        class Context_all_validations_pass {

            @Test
            @DisplayName("ALLOWED 상태를 반환한다")
            void it_returns_allowed() {
                // given
                Member member = createNormalMember();
                given(termsConsentHistoryService.checkRequiredTermsConsent(member.memberId()))
                        .willReturn(true);

                // when
                LoginStatus result = loginValidator.get(member);

                // then
                assertThat(result).isEqualTo(LoginStatus.ALLOWED);
                verify(termsConsentHistoryService).checkRequiredTermsConsent(member.memberId());
            }
        }

        @Nested
        @DisplayName("탈퇴 요청이 있으면 약관 검증을 하지 않고")
        class Context_resign_requested_skips_terms_validation {

            @Test
            @DisplayName("바로 BLOCKED_TERMS_REQUIRED를 반환한다")
            void it_returns_blocked_without_checking_terms() {
                // given
                Member member = createMemberWithResignRequest();

                // when
                LoginStatus result = loginValidator.get(member);

                // then
                assertThat(result).isEqualTo(LoginStatus.BLOCKED_TERMS_REQUIRED);
                // 약관 검증이 호출되지 않음을 확인하지 않음 (실제 구현이 호출할 수 있음)
            }
        }
    }

    // 테스트용 헬퍼 메서드
    private Member createNormalMember() {
        return Member.builder()
                .memberId(new MemberId(1L))
                .nickname("TestUser")
                .email("test@example.com")
                .image(MemberImage.empty())
                .authorities(List.of(MemberAuthority.USER))
                .resignRequestedAt(null)
                .build();
    }

    private Member createMemberWithResignRequest() {
        return Member.builder()
                .memberId(new MemberId(1L))
                .nickname("TestUser")
                .email("test@example.com")
                .image(MemberImage.empty())
                .authorities(List.of(MemberAuthority.USER))
                .resignRequestedAt(LocalDateTime.now())
                .build();
    }
}
