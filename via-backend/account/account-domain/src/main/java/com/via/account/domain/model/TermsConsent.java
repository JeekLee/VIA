package com.via.account.domain.model;

import com.via.account.domain.model.id.MemberId;
import com.via.account.domain.model.id.TermsConsentId;
import com.via.account.domain.model.id.TermsId;
import lombok.Builder;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Builder
public record TermsConsent(
        TermsConsentId termsConsentId,
        MemberId memberId,
        TermsId termsId,
        boolean withdrawn,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
){
    public static TermsConsent create(MemberId memberId, TermsId termsId) {
        return TermsConsent.builder()
                .memberId(memberId)
                .termsId(termsId)
                .withdrawn(false)
                .createdAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .updatedAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .build();
    }

    public TermsConsent withdraw() {
        return TermsConsent.builder()
                .termsConsentId(this.termsConsentId)
                .memberId(this.memberId)
                .termsId(this.termsId)
                .withdrawn(true)
                .createdAt(this.createdAt)
                .updatedAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .build();
    }
}
