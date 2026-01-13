package com.via.account.api.controller;

import com.via.account.api.api.TermsConsentApi;
import com.via.account.app.service.TermsConsentService;
import com.via.account.domain.model.id.MemberId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TermsConsentController implements TermsConsentApi {
    private final TermsConsentService termsConsentService;

    @Override
    public ResponseEntity<Void> agreeTermsOfService(Long userId, List<Long> termsIdList) {
        termsConsentService.agree(new MemberId(userId), termsIdList);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> withdrawTermsOfService(Long userId, List<Long> termsIdList) {
        termsConsentService.withdraw(new MemberId(userId), termsIdList);
        return ResponseEntity.ok().build();
    }
}
