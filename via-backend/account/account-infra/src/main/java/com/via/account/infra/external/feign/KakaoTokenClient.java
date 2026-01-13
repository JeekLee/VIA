package com.via.account.infra.external.feign;

import com.via.account.infra.external.dto.GoogleAccessToken;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "kakaoTokenClient", url = "${app.oauth2.kakao.token-url}")
public interface KakaoTokenClient {
    @PostMapping
    @Headers("Content-Type: application/x-www-form-urlencoded")
    GoogleAccessToken exchangeCodeForToken(
            @RequestParam("code") String code,
            @RequestParam("client_id") String clientId,
            @RequestParam("client_secret") String clientSecret,
            @RequestParam("redirect_uri") String redirectUri,
            @RequestParam("grant_type") String grantType,
            @RequestParam("code_verifier") String codeVerifier
    );
}
