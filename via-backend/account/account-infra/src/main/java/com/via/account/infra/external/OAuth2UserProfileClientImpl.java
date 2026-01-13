package com.via.account.infra.external;

import com.via.account.infra.external.feign.KakaoUserProfileClient;
import com.via.core.error.ExceptionCreator;
import com.via.account.infra.external.feign.GoogleUserProfileClient;
import com.via.account.app.external.OAuth2UserProfileClient;
import com.via.account.domain.enums.OAuth2Provider;
import com.via.account.domain.model.OAuth2UserInfo;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.via.account.infra.exception.OAuth2ClientException.FAILED_TO_GET_USER_PROFILE;
import static com.via.account.infra.exception.OAuth2ClientException.PROVIDER_NOT_SUPPORTED;

@Service
@Slf4j
@RequiredArgsConstructor
public class OAuth2UserProfileClientImpl implements OAuth2UserProfileClient {
    private final GoogleUserProfileClient googleUserProfileClient;
    private final KakaoUserProfileClient kakaoUserProfileClient;

    public OAuth2UserInfo getUserProfile(OAuth2Provider provider, String accessToken) {
        try {
            return switch (provider) {
                case GOOGLE -> googleUserProfileClient.getUserInfo("Bearer " + accessToken);
                case KAKAO -> kakaoUserProfileClient.getUserInfo("Bearer " + accessToken);
                default -> throw ExceptionCreator.create(PROVIDER_NOT_SUPPORTED, "[OAuth2UserProfileService] Provider: " + provider);
            };
        }
        catch (FeignException e) {
            throw ExceptionCreator.create(FAILED_TO_GET_USER_PROFILE, provider.name() + " oAuth2 Provider message: " + e.getMessage());
        }
    }
}
