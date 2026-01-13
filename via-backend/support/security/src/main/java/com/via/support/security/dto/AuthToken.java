package com.via.support.security.dto;

import com.via.support.security.enums.AuthTokenType;

import java.time.LocalDateTime;

public record AuthToken(
        String token,
        AuthTokenType type,
        LocalDateTime expiresAt
) { }
