package com.via.common.infra.career.impl;

import com.via.common.app.career.repository.CorporationRepository;
import com.via.common.domain.career.model.Corporation;
import com.via.common.infra.career.mapper.CorporationMapper;
import com.via.common.infra.career.search.repository.CorporationDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CorporationRepositoryImpl implements CorporationRepository {
    private final CorporationDocumentRepository corporationDocumentRepository;
    private final CorporationMapper mapper;

    @Override
    public void saveAll(List<Corporation> corporations) {
        corporationDocumentRepository.saveAll(corporations.stream().map(mapper::fromDomain).toList());
    }
}
