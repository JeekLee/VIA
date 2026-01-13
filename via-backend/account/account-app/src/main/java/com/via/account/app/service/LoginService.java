package com.via.account.app.service;

import com.via.account.app.dto.LoginContext;
import com.via.account.app.enums.LoginStatus;
import com.via.account.app.external.OAuth2TokenClient;
import com.via.account.app.external.OAuth2UserProfileClient;
import com.via.account.domain.model.*;
import com.via.core.error.ExceptionCreator;
import com.via.account.app.repository.MemberRepository;
import com.via.account.app.repository.OAuth2AccountRepository;
import com.via.account.app.repository.OAuth2ContextRepository;
import com.via.account.app.repository.RefreshTokenRepository;
import com.via.account.domain.enums.OAuth2Provider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.via.account.app.exception.LoginException.*;


@Service
@Slf4j
@RequiredArgsConstructor
public class LoginService {
    private final OAuth2TokenClient oAuth2TokenClient;
    private final OAuth2UserProfileClient oAuth2UserProfileClient;
    private final MemberManager memberManager;

    private final LoginValidator loginValidator;

    private final OAuth2AccountRepository oAuth2AccountRepository;
    private final OAuth2ContextRepository oAuth2ContextRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public LoginContext login(OAuth2Provider provider, String code, String state) {
        OAuth2Context oAuth2Context = oAuth2ContextRepository.findByState(state)
                .orElseThrow(() -> ExceptionCreator.create(OAUTH2_CONTEXT_NOT_FOUND, "state: " + state));
        OAuth2AccessToken oAuth2AccessToken = oAuth2TokenClient.getAccessToken(provider, code, oAuth2Context.getCodeVerifier(), oAuth2Context.getRedirectUri());
        OAuth2UserInfo oAuth2UserInfo = oAuth2UserProfileClient.getUserProfile(provider, oAuth2AccessToken.getAccessToken());

        Optional<OAuth2Account> oAuth2AccountOptional = oAuth2AccountRepository.findByOAuth2ProviderAndEmail(provider, oAuth2UserInfo.getEmail());

        if (oAuth2AccountOptional.isEmpty()) {
            Member member = memberManager.createAndSave(oAuth2UserInfo.getEmail(), oAuth2UserInfo.getImageUrl());
            oAuth2AccountRepository.save(OAuth2Account.createInitialAccount(member.memberId(), provider, oAuth2UserInfo.getEmail()));
            return LoginContext.create(member, LoginStatus.BLOCKED_NEW_MEMBER, oAuth2Context.getRedirectPath());
        }

        Member member = memberRepository.findById(oAuth2AccountOptional.get().memberId())
                .orElseThrow(() -> ExceptionCreator.create(MEMBER_NOT_FOUND, "memberId: " + oAuth2AccountOptional.get().memberId()));
        return LoginContext.create(member, loginValidator.get(member), oAuth2Context.getRedirectPath());
    }

    @Transactional
    public void logOut(String token) {
        refreshTokenRepository.deleteByToken(token);
    }

    @Transactional
    public LoginContext getContextByToken(String token) {
        if (token == null) throw ExceptionCreator.create(SESSION_NOT_FOUND, "token: null");
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> ExceptionCreator.create(SESSION_NOT_FOUND, "token: " + token));
        Member member = memberRepository.findById(refreshToken.memberId())
                .orElseThrow(() -> ExceptionCreator.create(MEMBER_NOT_FOUND, "memberId: " + refreshToken.memberId().id()));
        return LoginContext.create(member, loginValidator.get(member), null);
    }
}