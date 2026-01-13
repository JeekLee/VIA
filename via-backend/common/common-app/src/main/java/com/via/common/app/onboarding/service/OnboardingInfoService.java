package com.via.common.app.onboarding.service;

import com.via.common.app.onboarding.dao.OnboardingInfoDao;
import com.via.common.app.onboarding.dto.OnboardingInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OnboardingInfoService {
    private final OnboardingInfoDao onboardingInfoDao;

    @Transactional(readOnly = true)
    public List<OnboardingInfo> getOnboardingInfo() {
        return onboardingInfoDao.findAll();
    }
}
