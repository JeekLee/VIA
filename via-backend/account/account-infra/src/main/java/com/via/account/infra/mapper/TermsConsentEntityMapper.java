package com.via.account.infra.mapper;

import com.via.account.domain.model.TermsConsent;
import com.via.account.domain.model.id.MemberId;
import com.via.account.domain.model.id.TermsConsentId;
import com.via.account.domain.model.id.TermsId;
import com.via.account.infra.entity.TermsConsentJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class TermsConsentEntityMapper {
    public static TermsConsentJpaEntity fromDomain(TermsConsent termsConsent) {
        return TermsConsentJpaEntity.builder()
                .id(termsConsent.termsConsentId() == null ? null : termsConsent.termsConsentId().id())
                .memberId(termsConsent.memberId().id())
                .termsId(termsConsent.termsId().id())
                .withdrawn(termsConsent.withdrawn())
                .createdAt(termsConsent.createdAt())
                .updatedAt(termsConsent.updatedAt())
                .build();
    }

    public TermsConsent toDomain(TermsConsentJpaEntity entity) {
        return TermsConsent.builder()
                .termsConsentId(new TermsConsentId(entity.getId()))
                .memberId(new MemberId(entity.getMemberId()))
                .termsId(new TermsId(entity.getTermsId()))
                .withdrawn(entity.getWithdrawn())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
