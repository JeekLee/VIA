package com.via.account.app.external;

import com.via.account.domain.enums.OAuth2Provider;
import com.via.account.domain.model.OAuth2UserInfo;

public interface OAuth2UserProfileClient {
    OAuth2UserInfo getUserProfile(OAuth2Provider provider, String accessToken);
}
