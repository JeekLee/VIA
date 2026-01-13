package com.via.account.domain.model;

import com.via.account.domain.exception.OAuth2Exception;
import com.via.core.error.ExceptionCreator;

import java.util.Base64;

public record CodeChallenge(
        String value
) {
    public static CodeChallenge create(String codeVerifier) {
        try {
            java.security.MessageDigest digest = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(codeVerifier.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            return new CodeChallenge(Base64.getUrlEncoder().withoutPadding().encodeToString(hash));
        } catch (Exception e) {
            throw ExceptionCreator.create(OAuth2Exception.FAILED_TO_CREATE_CODE_CHALLENGE, e.getMessage());
        }
    }
}
