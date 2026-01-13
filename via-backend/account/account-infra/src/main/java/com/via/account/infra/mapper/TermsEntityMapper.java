package com.via.account.infra.mapper;

import com.via.account.domain.model.Terms;
import com.via.account.domain.model.id.TermsCategoryId;
import com.via.account.domain.model.id.TermsId;
import com.via.account.infra.entity.TermsJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class TermsEntityMapper {
    public static TermsJpaEntity fromDomain(Terms terms) {
        return TermsJpaEntity.builder()
                .id(terms.termsId() == null ? null : terms.termsId().id())
                .majorVersion(terms.majorVersion())
                .minorVersion(terms.minorVersion())
                .title(terms.title())
                .content(terms.content())
                .effectiveAt(terms.effectiveAt())
                .termsCategoryId(terms.termsCategoryId().id())
                .build();
    }

    public Terms toDomain(TermsJpaEntity entity) {
        return Terms.builder()
                .termsId(new TermsId(entity.getId()))
                .termsCategoryId(new TermsCategoryId(entity.getTermsCategoryId()))
                .majorVersion(entity.getMajorVersion())
                .minorVersion(entity.getMinorVersion())
                .title(entity.getTitle())
                .content(entity.getContent())
                .effectiveAt(entity.getEffectiveAt())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
