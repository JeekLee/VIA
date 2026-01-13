package com.via.account.domain.model;

import com.via.account.domain.enums.OAuth2Provider;
import com.via.account.domain.model.id.MemberId;
import com.via.account.domain.model.id.OAuth2AccountId;
import lombok.Builder;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Builder
public record OAuth2Account(
        OAuth2AccountId oAuth2AccountId,
        MemberId memberId,

        OAuth2Provider provider,
        String email,

        boolean initialAccount,

        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static OAuth2Account createInitialAccount(MemberId memberId, OAuth2Provider provider, String email) {
        return OAuth2Account.builder()
                .memberId(memberId)
                .provider(provider)
                .email(email)
                .initialAccount(true)
                .createdAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .updatedAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .build();
    }
}
