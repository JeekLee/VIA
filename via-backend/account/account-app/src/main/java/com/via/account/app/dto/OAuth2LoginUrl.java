package com.via.account.app.dto;

import com.via.account.domain.enums.OAuth2Provider;

import java.time.LocalDateTime;

public record OAuth2LoginUrl(
        String url,
        String state,
        OAuth2Provider provider,
        LocalDateTime expiresAt
) {

}
