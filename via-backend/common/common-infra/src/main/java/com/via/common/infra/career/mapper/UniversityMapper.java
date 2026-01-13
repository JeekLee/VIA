package com.via.common.infra.career.mapper;

import com.via.common.domain.career.model.University;
import com.via.common.infra.career.search.document.UniversityDocument;
import org.springframework.stereotype.Component;

@Component
public class UniversityMapper {
    public UniversityDocument fromDomain(University university) {
        return UniversityDocument.builder()
                .code(university.id().code())
                .name(university.name())
                .build();
    }
}