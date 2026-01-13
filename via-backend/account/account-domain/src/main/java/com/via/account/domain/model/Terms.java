package com.via.account.domain.model;

import com.via.account.domain.model.id.TermsCategoryId;
import com.via.account.domain.model.id.TermsId;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record Terms(
        TermsId termsId,
        TermsCategoryId termsCategoryId,
        int majorVersion,
        int minorVersion,
        String title,
        String content,
        LocalDateTime effectiveAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static Terms create(TermsCategory termsCategory, int majorVersion, int minorVersion, String title, String content, LocalDateTime effectiveAt) {
        return Terms.builder()
                .termsCategoryId(termsCategory.termsCategoryId())
                .majorVersion(majorVersion)
                .minorVersion(minorVersion)
                .title(title)
                .content(content)
                .effectiveAt(effectiveAt)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
