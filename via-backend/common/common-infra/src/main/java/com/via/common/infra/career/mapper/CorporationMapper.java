package com.via.common.infra.career.mapper;

import com.via.common.domain.career.model.Corporation;
import com.via.common.infra.career.search.document.CorporationDocument;
import org.springframework.stereotype.Component;

@Component
public class CorporationMapper {
    public CorporationDocument fromDomain(Corporation corporation) {
        return CorporationDocument.builder()
                .code(corporation.id().code())
                .name(corporation.name())
                .build();
    }
}
