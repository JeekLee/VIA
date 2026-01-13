package com.via.account.infra.external.dto;

import com.via.account.domain.enums.OAuth2Provider;
import com.via.account.domain.model.OAuth2UserInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GoogleUserInfo extends OAuth2UserInfo {
    @JsonIgnore
    private final OAuth2Provider provider = OAuth2Provider.GOOGLE;

    @JsonProperty("sub")
    private String providerId;

    @JsonProperty("email")
    private String email;

    @JsonProperty("picture")
    private String imageUrl;
}
