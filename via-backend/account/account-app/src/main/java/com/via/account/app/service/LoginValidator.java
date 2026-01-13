package com.via.account.app.service;

import com.via.account.app.enums.LoginStatus;
import com.via.account.domain.model.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginValidator {
    private final TermsConsentHistoryService termsConsentHistoryService;

    @Transactional(readOnly = true)
    public LoginStatus get(Member member) {
        if (member.resignRequestedAt() != null) {
            return LoginStatus.BLOCKED_TERMS_REQUIRED;
        }

        if (!termsConsentHistoryService.checkRequiredTermsConsent(member.memberId())) {
            return LoginStatus.BLOCKED_TERMS_REQUIRED;
        }

        return LoginStatus.ALLOWED;

    }
}
