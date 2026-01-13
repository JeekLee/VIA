package com.via.common.app.onboarding.dao;

import com.via.common.app.onboarding.dto.OnboardingInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OnboardingInfoDao {
    List<OnboardingInfo> findAll();
}
