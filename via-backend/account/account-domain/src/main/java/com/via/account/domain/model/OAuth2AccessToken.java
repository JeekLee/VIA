package com.via.account.domain.model;

public abstract class OAuth2AccessToken {
    public abstract String getAccessToken();
    public abstract Long getExpiresIn();
}
