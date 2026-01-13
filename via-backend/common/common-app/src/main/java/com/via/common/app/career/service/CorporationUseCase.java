package com.via.common.app.career.service;

import com.via.common.app.career.dao.CorporationInfoDao;
import com.via.common.app.career.dto.CorporationInfo;
import com.via.common.app.career.dto.CreateCorporation;
import com.via.common.app.career.repository.CorporationRepository;
import com.via.common.domain.career.model.Corporation;
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
public class CorporationUseCase {
    private final CorporationInfoDao corporationInfoDao;
    private final CorporationRepository corporationRepository;

    public Page<CorporationInfo> searchCorporation(String name, Pageable pageable) {
        return corporationInfoDao.findByName(name, pageable);
    }

    @Transactional
    public void createAndSave(List<CreateCorporation> createCorporationList) {
        List<Corporation> corporations = createCorporationList.stream()
                .map(createCorporation -> Corporation.create(createCorporation.code(), createCorporation.name()))
                .toList();
        corporationRepository.saveAll(corporations);
    }
}
