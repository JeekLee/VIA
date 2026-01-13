package com.via.account.domain.model;

import com.via.account.domain.model.id.MemberId;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record RefreshToken(
        String token,
        MemberId memberId,
        LocalDateTime expiresAt,
        int useCount
) {
    public static RefreshToken create(MemberId memberId, String token, LocalDateTime expiresAt) {
        return RefreshToken.builder()
                .token(token)
                .memberId(memberId)
                .expiresAt(expiresAt)
                .useCount(0)
                .build();
    }

    public RefreshToken increaseUseCount() {
        return RefreshToken.builder()
                .token(this.token())
                .memberId(this.memberId())
                .expiresAt(this.expiresAt())
                .useCount(useCount + 1)
                .build();
    }
}
