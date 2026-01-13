package com.via.account.infra.external;

import com.via.core.error.ExceptionCreator;
import com.via.account.app.external.OAuth2TokenClient;
import com.via.account.infra.external.feign.GoogleTokenClient;
import com.via.account.infra.external.feign.KakaoTokenClient;
import com.via.account.domain.enums.OAuth2Provider;
import com.via.account.domain.model.OAuth2AccessToken;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.via.account.infra.exception.OAuth2ClientException.FAILED_TO_GET_ACCESS_TOKEN;
import static com.via.account.infra.exception.OAuth2ClientException.PROVIDER_NOT_SUPPORTED;

@Service
@Slf4j
@RequiredArgsConstructor
public class OAuth2TokenClientImpl implements OAuth2TokenClient {
    private final OAuth2Properties oAuth2Properties;
    private final GoogleTokenClient googleTokenClient;
    private final KakaoTokenClient kakaoTokenClient;

    public OAuth2AccessToken getAccessToken(OAuth2Provider provider, String code, String codeVerifier, String redirectUri) {
        try {
            return switch (provider) {
                case GOOGLE -> googleTokenClient.exchangeCodeForAccessToken(
                        code,
                        oAuth2Properties.getGoogle().getClientId(),
                        oAuth2Properties.getGoogle().getClientSecret(),
                        redirectUri,
                        "authorization_code",
                        codeVerifier
                );
                case KAKAO -> kakaoTokenClient.exchangeCodeForToken(
                        code,
                        oAuth2Properties.getKakao().getClientId(),
                        oAuth2Properties.getKakao().getClientSecret(),
                        redirectUri,
                        "authorization_code",
                        codeVerifier
                );
                default -> throw ExceptionCreator.create(PROVIDER_NOT_SUPPORTED, "[OAuth2TokenService] Provider: " + provider);
            };
        }
        catch (FeignException e) {
            throw ExceptionCreator.create(FAILED_TO_GET_ACCESS_TOKEN, provider.name() + " oAuth2 message: " + e.getMessage());
        }
    }
}
