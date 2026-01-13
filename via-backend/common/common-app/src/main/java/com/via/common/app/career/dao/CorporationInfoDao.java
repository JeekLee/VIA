package com.via.common.app.career.dao;

import com.via.common.app.career.dto.CorporationInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CorporationInfoDao {
    Page<CorporationInfo> findByName(String name, Pageable pageable);
}
