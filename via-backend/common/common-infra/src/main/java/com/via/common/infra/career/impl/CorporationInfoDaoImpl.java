package com.via.common.infra.career.impl;

import com.via.common.app.career.dao.CorporationInfoDao;
import com.via.common.app.career.dto.CorporationInfo;
import com.via.common.infra.career.search.repository.CorporationDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CorporationInfoDaoImpl implements CorporationInfoDao {
    private final CorporationDocumentRepository corporationDocumentRepository;

    @Override
    public Page<CorporationInfo> findByName(String name, Pageable pageable) {
        return corporationDocumentRepository.findByName(name, pageable)
                .map(doc -> new CorporationInfo(doc.getCode(), doc.getName()));
    }
}
