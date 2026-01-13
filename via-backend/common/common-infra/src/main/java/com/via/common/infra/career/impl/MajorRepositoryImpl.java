package com.via.common.infra.career.impl;

import com.via.common.app.career.repository.MajorRepository;
import com.via.common.domain.career.model.Major;
import com.via.common.infra.career.mapper.MajorMapper;
import com.via.common.infra.career.search.repository.MajorDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MajorRepositoryImpl implements MajorRepository {
    private final MajorDocumentRepository majorDocumentRepository;
    private final MajorMapper mapper;

    @Override
    public void saveAll(List<Major> majors) {
        majorDocumentRepository.saveAll(majors.stream().map(mapper::fromDomain).toList());
    }
}
