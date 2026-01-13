package com.via.account.app.external;

import com.via.account.domain.enums.OAuth2Provider;
import com.via.account.domain.model.OAuth2AccessToken;

public interface OAuth2TokenClient {
    OAuth2AccessToken getAccessToken(OAuth2Provider provider, String code, String codeVerifier, String redirectUri);
}
