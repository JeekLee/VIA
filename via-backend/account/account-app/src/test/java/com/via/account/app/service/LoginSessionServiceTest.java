package com.via.account.app.service;

import com.via.account.app.repository.RefreshTokenRepository;
import com.via.account.domain.model.RefreshToken;
import com.via.account.domain.model.id.MemberId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@Tag("UNIT_TEST")
@ExtendWith(MockitoExtension.class)
@DisplayName("LoginSessionService 단위 테스트")
class LoginSessionServiceTest {

    @InjectMocks
    private LoginSessionService loginSessionService;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Nested
    @DisplayName("replace 메서드는")
    class Describe_replace {

        @Test
        @DisplayName("기존 토큰을 삭제하고 새 토큰을 저장한다")
        void it_deletes_old_token_and_saves_new_token() {
            // given
            MemberId memberId = new MemberId(1L);
            String oldToken = "old-refresh-token";
            String newToken = "new-refresh-token";
            LocalDateTime expiresAt = LocalDateTime.now().plusDays(7);

            // when
            loginSessionService.replace(memberId, oldToken, newToken, expiresAt);

            // then
            verify(refreshTokenRepository).deleteByToken(oldToken);
            
            ArgumentCaptor<RefreshToken> captor = ArgumentCaptor.forClass(RefreshToken.class);
            verify(refreshTokenRepository).save(captor.capture());
            
            RefreshToken savedToken = captor.getValue();
            assertThat(savedToken.memberId()).isEqualTo(memberId);
            assertThat(savedToken.token()).isEqualTo(newToken);
            assertThat(savedToken.expiresAt()).isEqualTo(expiresAt);
        }

        @Test
        @DisplayName("삭제와 저장이 순서대로 실행된다")
        void it_executes_delete_before_save() {
            // given
            MemberId memberId = new MemberId(1L);
            String oldToken = "old-token";
            String newToken = "new-token";
            LocalDateTime expiresAt = LocalDateTime.now().plusDays(7);

            // when
            loginSessionService.replace(memberId, oldToken, newToken, expiresAt);

            // then
            var inOrder = org.mockito.Mockito.inOrder(refreshTokenRepository);
            inOrder.verify(refreshTokenRepository).deleteByToken(oldToken);
            inOrder.verify(refreshTokenRepository).save(any(RefreshToken.class));
        }
    }

    @Nested
    @DisplayName("create 메서드는")
    class Describe_create {

        @Test
        @DisplayName("새로운 리프레시 토큰을 생성하고 저장한다")
        void it_creates_and_saves_new_refresh_token() {
            // given
            MemberId memberId = new MemberId(1L);
            String token = "refresh-token";
            LocalDateTime expiresAt = LocalDateTime.now().plusDays(7);

            // when
            loginSessionService.create(memberId, token, expiresAt);

            // then
            ArgumentCaptor<RefreshToken> captor = ArgumentCaptor.forClass(RefreshToken.class);
            verify(refreshTokenRepository).save(captor.capture());
            
            RefreshToken savedToken = captor.getValue();
            assertThat(savedToken.memberId()).isEqualTo(memberId);
            assertThat(savedToken.token()).isEqualTo(token);
            assertThat(savedToken.expiresAt()).isEqualTo(expiresAt);
        }

        @Test
        @DisplayName("만료 시간이 정확하게 설정된다")
        void it_sets_expiration_time_correctly() {
            // given
            MemberId memberId = new MemberId(1L);
            String token = "test-token";
            LocalDateTime expiresAt = LocalDateTime.of(2025, 12, 31, 23, 59, 59);

            // when
            loginSessionService.create(memberId, token, expiresAt);

            // then
            ArgumentCaptor<RefreshToken> captor = ArgumentCaptor.forClass(RefreshToken.class);
            verify(refreshTokenRepository).save(captor.capture());
            
            RefreshToken savedToken = captor.getValue();
            assertThat(savedToken.expiresAt()).isEqualTo(expiresAt);
        }
    }
}
