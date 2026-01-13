package com.via.account.app.service;

import com.via.account.app.dto.OAuth2LoginUrl;
import com.via.account.app.external.OAuth2LoginUriProvider;
import com.via.account.app.repository.OAuth2ContextRepository;
import com.via.account.domain.enums.OAuth2Provider;
import com.via.account.domain.model.CodeChallenge;
import com.via.account.domain.model.OAuth2Context;
import com.via.account.domain.service.OAuth2ContextService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URI;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuth2UrlService {
    private final OAuth2LoginUriProvider oAuth2LoginUriProvider;

    private final OAuth2ContextService oAuth2ContextService;
    private final OAuth2ContextRepository OAuth2ContextRepository;

    public OAuth2LoginUrl generateLoginUrl(OAuth2Provider oAuth2Provider, String codeVerifier, String redirectPath, String redirectUri) {
        CodeChallenge codeChallenge = CodeChallenge.create(codeVerifier);
        OAuth2Context oauth2Context = oAuth2ContextService.createContext(oAuth2Provider, codeVerifier, redirectPath, redirectUri);
        oauth2Context = OAuth2ContextRepository.save(oauth2Context);
        URI signInUri = oAuth2LoginUriProvider.create(oAuth2Provider, redirectUri, oauth2Context.getState(), codeChallenge.value());

        return new OAuth2LoginUrl(signInUri.toString(), oauth2Context.getState(), oAuth2Provider, oauth2Context.getExpiresAt());
    }
}
