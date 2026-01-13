package com.via.account.infra.external;

import com.via.core.error.ExceptionCreator;
import com.via.account.domain.enums.OAuth2Provider;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import static com.via.account.infra.exception.OAuth2ClientException.PROVIDER_NOT_SUPPORTED;

@Data
@Configuration
@ConfigurationProperties(prefix = "app.oauth2")
public class OAuth2Properties {
    private Provider google;
    private Provider kakao;

    @Data
    public static class Provider {
        private String clientId;
        private String clientSecret;

        private String authorizationUrl;
        private String tokenUrl;
        private String userInfoUrl;

        private String scope;
    }

    public Provider getProvider(OAuth2Provider provider) {
        return switch (provider) {
            case GOOGLE -> google;
            case KAKAO -> kakao;
            default -> throw ExceptionCreator.create(PROVIDER_NOT_SUPPORTED, "Provider: " + provider.getValue());
        };
    }
}
