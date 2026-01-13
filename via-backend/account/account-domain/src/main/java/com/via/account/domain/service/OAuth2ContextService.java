package com.via.account.domain.service;

import com.via.account.domain.enums.OAuth2Provider;
import com.via.account.domain.model.OAuth2Context;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class OAuth2ContextService {
    private static final SecureRandom secureRandom = new SecureRandom();

    public OAuth2Context createContext(OAuth2Provider provider, String codeVerifier, String redirectPath, String redirectUri) {
        byte[] bytes = new byte[32];
        secureRandom.nextBytes(bytes);
        String state = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);

        return OAuth2Context.builder()
                .state(state)
                .provider(provider)
                .codeVerifier(codeVerifier)
                .redirectPath(redirectPath)
                .redirectUri(redirectUri)
                .build();
    }
}
