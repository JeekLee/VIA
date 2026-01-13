package com.via.common.api.controller;

import com.via.common.api.api.OnboardingInfoApi;
import com.via.common.app.onboarding.dto.OnboardingInfo;
import com.via.common.app.onboarding.service.OnboardingInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OnboardingInfoController implements OnboardingInfoApi {
    private final OnboardingInfoService onboardingInfoService;

    @Override
    public ResponseEntity<List<OnboardingInfo>> getOnboardingDocuments() {
        return ResponseEntity.ok(onboardingInfoService.getOnboardingInfo());
    }
}
