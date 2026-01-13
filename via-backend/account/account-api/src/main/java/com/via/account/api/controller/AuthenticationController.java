package com.via.account.api.controller;

import com.via.account.api.api.AuthenticationApi;
import com.via.account.api.request.OAuth2LogInRequest;
import com.via.account.api.request.OAuth2UrlRequest;
import com.via.account.api.response.LoginResponse;
import com.via.account.api.utils.AuthorityMapper;
import com.via.account.app.dto.LoginContext;
import com.via.account.app.dto.OAuth2LoginUrl;
import com.via.account.app.service.LoginService;
import com.via.account.app.service.LoginSessionService;
import com.via.account.app.service.OAuth2UrlService;
import com.via.support.security.dto.AuthToken;
import com.via.support.security.jwt.JwtHeadersProvider;
import com.via.support.security.jwt.JwtProvider;
import com.via.support.security.properties.JwtProperties;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AuthenticationController implements AuthenticationApi {
    private final JwtProperties jwtProperties;

    private final JwtProvider jwtProvider;
    private final JwtHeadersProvider jwtHeadersProvider;

    private final LoginSessionService loginSessionService;

    private final OAuth2UrlService oAuth2UrlService;
    private final LoginService loginService;


    @Override
    public ResponseEntity<OAuth2LoginUrl> getOAuth2LogInUrl(OAuth2UrlRequest request) {
        return ResponseEntity.ok(oAuth2UrlService.generateLoginUrl(
                request.getProvider(), request.getCodeVerifier(), request.getRedirectPath(), request.getRedirectUri())
        );
    }

    @Override
    public ResponseEntity<LoginResponse> oAuth2LogIn(OAuth2LogInRequest request) {
        LoginContext context = loginService.login(request.getProvider(), request.getCode(), request.getState());
        AuthToken refreshToken = jwtProvider.generateRefreshToken(context.memberId().id());
        loginSessionService.create(context.memberId(), refreshToken.token(), refreshToken.expiresAt());

        if (context.status().isBlocked()) {
            AuthToken temporaryToken = jwtProvider.generateTemporaryToken(context.memberId().id());
            return ResponseEntity.ok()
                    .headers(jwtHeadersProvider.createHeaders(List.of(refreshToken, temporaryToken)))
                    .body(LoginResponse.fromContext(context));
        }

        AuthToken accessToken = jwtProvider.generateAccessToken(
                context.memberId().id(),
                context.nickname(),
                AuthorityMapper.toAuthorities(context.authorities())
        );

        return ResponseEntity.ok()
                .headers(jwtHeadersProvider.createHeaders(List.of(refreshToken, accessToken)))
                .body(LoginResponse.fromContext(context));
    }

    @Override
    public ResponseEntity<Void> logOut(HttpServletRequest request) {
        String oldToken = Optional.ofNullable(request.getCookies())
                .stream()
                .flatMap(Arrays::stream)
                .filter(cookie -> cookie.getName().equals(jwtProperties.getRefreshToken().getTokenKey()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);

        loginService.logOut(oldToken);

        return ResponseEntity.noContent()
                .headers(jwtHeadersProvider.createEmptyHeaders())
                .build();
    }

    @Override
    public ResponseEntity<LoginResponse> refreshAuthenticationToken(HttpServletRequest request) {
        String oldToken = Optional.ofNullable(request.getCookies())
                .stream()
                .flatMap(Arrays::stream)
                .filter(cookie -> cookie.getName().equals(jwtProperties.getRefreshToken().getTokenKey()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);

        LoginContext context = loginService.getContextByToken(oldToken);
        AuthToken refreshToken = jwtProvider.generateRefreshToken(context.memberId().id());
        loginSessionService.replace(context.memberId(), oldToken, refreshToken.token(), refreshToken.expiresAt());

        if (context.status().isBlocked()) {
            AuthToken temporaryToken = jwtProvider.generateTemporaryToken(context.memberId().id());
            return ResponseEntity.ok()
                    .headers(jwtHeadersProvider.createHeaders(List.of(refreshToken, temporaryToken)))
                    .body(LoginResponse.fromContext(context));
        }

        AuthToken accessToken = jwtProvider.generateAccessToken(
                context.memberId().id(),
                context.nickname(),
                AuthorityMapper.toAuthorities(context.authorities())
        );

        return ResponseEntity.ok()
                .headers(jwtHeadersProvider.createHeaders(List.of(refreshToken, accessToken)))
                .body(LoginResponse.fromContext(context));
    }
}
