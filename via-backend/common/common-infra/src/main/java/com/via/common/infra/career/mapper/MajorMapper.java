package com.via.common.infra.career.mapper;

import com.via.common.domain.career.model.Major;
import com.via.common.infra.career.search.document.MajorDocument;
import org.springframework.stereotype.Component;

@Component
public class MajorMapper {
    public MajorDocument fromDomain(Major major) {
        return MajorDocument.builder()
                .code(major.id().code())
                .name(major.name())
                .build();
    }
}
