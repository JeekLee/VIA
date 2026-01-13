package com.via.account.domain.model;

import com.via.account.domain.enums.OAuth2Provider;

public abstract class OAuth2UserInfo {
    public abstract OAuth2Provider getProvider();
    public abstract String getProviderId();
    public abstract String getEmail();
    public abstract String getImageUrl();
}
