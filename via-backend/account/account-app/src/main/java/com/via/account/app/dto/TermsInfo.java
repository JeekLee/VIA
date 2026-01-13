package com.via.account.app.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TermsInfo {
    private final Long termsId;
    private final boolean required;
    private final String version;
    private final String title;
    private final String content;
    private final LocalDateTime effectiveAt;

    @Builder
    public TermsInfo(Long termsId, boolean required, int majorVersion, int minorVersion, String title, String content, LocalDateTime effectiveAt) {
        this.termsId = termsId;
        this.required = required;
        this.version = "v" + majorVersion + "." + minorVersion;
        this.title = title;
        this.content = content;
        this.effectiveAt = effectiveAt;
    }
}
