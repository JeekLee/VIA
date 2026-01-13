package com.via.account.infra.external.dto;

import com.via.account.domain.model.OAuth2AccessToken;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GoogleAccessToken extends OAuth2AccessToken {
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("expires_in")
    private Long expiresIn;
}
