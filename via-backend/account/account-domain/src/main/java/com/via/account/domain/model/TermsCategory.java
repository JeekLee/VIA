package com.via.account.domain.model;

import com.via.account.domain.model.id.TermsCategoryId;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TermsCategory(
        TermsCategoryId termsCategoryId,
        boolean required,
        String name,
        int priority,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
){
    public static TermsCategory create(boolean required, String name, int priority) {
        return TermsCategory.builder()
                .required(required)
                .name(name)
                .priority(priority)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
