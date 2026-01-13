package com.via.account.app.external;

import com.via.account.domain.enums.OAuth2Provider;

import java.net.URI;

public interface OAuth2LoginUriProvider {
    URI create(OAuth2Provider oAuth2Provider, String redirectUri, String state, String codeChallenge);
}
