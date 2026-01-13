package com.via.common.app.career.service;

import com.via.common.app.career.dao.MajorInfoDao;
import com.via.common.app.career.dto.CreateMajor;
import com.via.common.app.career.dto.MajorInfo;
import com.via.common.app.career.repository.MajorRepository;
import com.via.common.domain.career.model.Major;
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
public class MajorUseCase {
    private final MajorInfoDao majorInfoDao;
    private final MajorRepository majorRepository;

    public Page<MajorInfo> searchMajor(String name, Pageable pageable) {
        return majorInfoDao.findByName(name, pageable);
    }

    @Transactional
    public void createAndSave(List<CreateMajor> createMajorList) {
        List<Major> majors = createMajorList.stream()
                .map(createMajor -> Major.create(createMajor.code(), createMajor.name()))
                .toList();
        majorRepository.saveAll(majors);
    }
}
