package com.via.common.domain.onboarding.model;

import com.via.common.domain.onboarding.model.id.OnboardingId;
import com.via.common.domain.onboarding.model.vo.OnboardingImage;
import lombok.Builder;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Builder
public record Onboarding(
    OnboardingId id,
    Integer priority,
    String title,
    String content,
    OnboardingImage image,
    Boolean activated,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    public static Onboarding create(Integer priority, String title, String content, OnboardingImage image) {
        return Onboarding.builder()
                .priority(priority)
                .title(title)
                .content(content)
                .image(image)
                .activated(true)
                .createdAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .updatedAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .build();
    }
}
