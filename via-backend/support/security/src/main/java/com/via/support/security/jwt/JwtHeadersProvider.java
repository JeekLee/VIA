package com.via.support.security.jwt;

import com.via.support.security.dto.AuthToken;
import com.via.support.security.properties.JwtProperties;
import com.via.support.security.utils.CookieUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtHeadersProvider {
    private final JwtProperties jwtProperties;
    private final JwtProvider jwtProvider;

    private String accessTokenKey;
    private String refreshTokenKey;
    private String temporaryTokenKey;

    @PostConstruct
    private void init() {
        this.accessTokenKey = jwtProperties.getAccessToken().getTokenKey();
        this.refreshTokenKey = jwtProperties.getRefreshToken().getTokenKey();
        this.temporaryTokenKey = jwtProperties.getTemporaryToken().getTokenKey();
    }

    public HttpHeaders createEmptyHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, CookieUtil.createEmptyCookie(accessTokenKey).toString());
        headers.add(HttpHeaders.SET_COOKIE, CookieUtil.createEmptyCookie(refreshTokenKey).toString());
        headers.add(HttpHeaders.SET_COOKIE, CookieUtil.createEmptyCookie(temporaryTokenKey).toString());

        return headers;
    }

    public HttpHeaders createHeaders(List<AuthToken> authTokenList) {
        HttpHeaders headers = createEmptyHeaders();

        for (AuthToken authToken : authTokenList) {
            switch (authToken.type()) {
                case ACCESS -> headers.add(HttpHeaders.SET_COOKIE,
                        CookieUtil.createCookie(accessTokenKey, authToken.token(), authToken.expiresAt()).toString()
                );
                case REFRESH -> headers.add(HttpHeaders.SET_COOKIE,
                        CookieUtil.createCookie(refreshTokenKey, authToken.token(), authToken.expiresAt()).toString()
                );
                case TEMPORARY -> headers.add(HttpHeaders.SET_COOKIE,
                        CookieUtil.createCookie(temporaryTokenKey, authToken.token(), authToken.expiresAt()).toString()
                );
            }
        }
        return headers;
    }
}
