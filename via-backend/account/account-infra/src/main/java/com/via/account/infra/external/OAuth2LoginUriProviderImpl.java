package com.via.account.infra.external;

import com.via.account.app.external.OAuth2LoginUriProvider;
import com.via.account.domain.enums.OAuth2Provider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@Slf4j
@RequiredArgsConstructor
public class OAuth2LoginUriProviderImpl implements OAuth2LoginUriProvider {
    private final OAuth2Properties oAuth2Properties;

    @Override
    public URI create(OAuth2Provider oAuth2Provider, String redirectUri, String state, String codeChallenge) {
        OAuth2Properties.Provider properties = oAuth2Properties.getProvider(oAuth2Provider);

        return UriComponentsBuilder
                .fromUriString(properties.getAuthorizationUrl())
                .queryParam("client_id", properties.getClientId())
                .queryParam("redirect_uri", redirectUri)
                .queryParam("response_type", "code")
                .queryParam("scope", properties.getScope())
                .queryParam("state", state)
                .queryParam("code_challenge", codeChallenge)
                .queryParam("code_challenge_method", "S256")
                .build()
                .toUri();
    }
}
