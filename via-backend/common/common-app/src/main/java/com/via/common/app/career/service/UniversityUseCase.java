package com.via.common.app.career.service;

import com.via.common.app.career.dao.UniversityInfoDao;
import com.via.common.app.career.dto.CreateUniversity;
import com.via.common.app.career.dto.UniversityInfo;
import com.via.common.app.career.repository.UniversityRepository;
import com.via.common.domain.career.model.University;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UniversityUseCase {
    private final UniversityInfoDao universityInfoDao;
    private final UniversityRepository universityRepository;

    public Page<UniversityInfo> searchUniversity(String name, Pageable pageable) {
        return universityInfoDao.findByName(name, pageable);
    }

    @Transactional
    public void createAndSave(List<CreateUniversity> createUniversityList) {
        List<University> universities = createUniversityList.stream()
                .map(createUniversity -> University.create(createUniversity.code(), createUniversity.name()))
                .toList();
        universityRepository.saveAll(universities);
    }
}
