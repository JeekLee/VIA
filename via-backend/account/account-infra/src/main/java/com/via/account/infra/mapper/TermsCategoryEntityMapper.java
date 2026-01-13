package com.via.account.infra.mapper;

import com.via.account.domain.model.TermsCategory;
import com.via.account.domain.model.id.TermsCategoryId;
import com.via.account.infra.entity.TermsCategoryJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class TermsCategoryEntityMapper {
    public TermsCategory toDomain(TermsCategoryJpaEntity entity) {
        return TermsCategory.builder()
                .termsCategoryId(new TermsCategoryId(entity.getId()))
                .required(entity.isRequired())
                .name(entity.getName())
                .priority(entity.getPriority())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static TermsCategoryJpaEntity fromDomain(TermsCategory termsCategory) {
        return TermsCategoryJpaEntity.builder()
                .id(termsCategory.termsCategoryId() == null ? null : termsCategory.termsCategoryId().id())
                .required(termsCategory.required())
                .name(termsCategory.name())
                .priority(termsCategory.priority())
                .createdAt(termsCategory.createdAt())
                .updatedAt(termsCategory.updatedAt())
                .build();
    }
}
