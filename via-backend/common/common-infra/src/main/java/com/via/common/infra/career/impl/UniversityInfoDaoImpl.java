package com.via.common.infra.career.impl;

import com.via.common.app.career.dao.UniversityInfoDao;
import com.via.common.app.career.dto.UniversityInfo;
import com.via.common.infra.career.search.repository.UniversityDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class UniversityInfoDaoImpl implements UniversityInfoDao {
    private final UniversityDocumentRepository universityDocumentRepository;

    @Override
    public Page<UniversityInfo> findByName(String name, Pageable pageable) {
        return universityDocumentRepository.findByName(name, pageable)
                .map(doc -> new UniversityInfo(doc.getCode(), doc.getName()));
    }
}
