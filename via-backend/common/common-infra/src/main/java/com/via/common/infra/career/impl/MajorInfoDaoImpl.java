package com.via.common.infra.career.impl;

import com.via.common.app.career.dao.MajorInfoDao;
import com.via.common.app.career.dto.MajorInfo;
import com.via.common.infra.career.search.repository.MajorDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MajorInfoDaoImpl implements MajorInfoDao {
    private final MajorDocumentRepository majorDocumentRepository;

    @Override
    public Page<MajorInfo> findByName(String name, Pageable pageable) {
        return majorDocumentRepository.findByName(name, pageable)
                .map(doc -> new MajorInfo(doc.getCode(), doc.getName()));
    }
}
