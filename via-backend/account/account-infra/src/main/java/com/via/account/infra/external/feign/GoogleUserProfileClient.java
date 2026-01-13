package com.via.account.infra.external.feign;

import com.via.account.infra.external.dto.GoogleUserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "googleUserProfileClient", url = "${app.oauth2.google.user-info-url}")
public interface GoogleUserProfileClient {
    @GetMapping
    GoogleUserInfo getUserInfo(@RequestHeader("Authorization") String bearerToken);
}
