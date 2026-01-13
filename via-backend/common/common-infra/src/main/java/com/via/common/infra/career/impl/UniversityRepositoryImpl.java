package com.via.common.infra.career.impl;

import com.via.common.app.career.repository.UniversityRepository;
import com.via.common.domain.career.model.University;
import com.via.common.infra.career.mapper.UniversityMapper;
import com.via.common.infra.career.search.repository.UniversityDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UniversityRepositoryImpl implements UniversityRepository {
    private final UniversityDocumentRepository universityDocumentRepository;
    private final UniversityMapper mapper;

    @Override
    public void saveAll(List<University> universities) {
        universityDocumentRepository.saveAll(universities.stream().map(mapper::fromDomain).toList());
    }
}
