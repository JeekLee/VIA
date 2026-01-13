package com.via.common.app.career.dao;

import com.via.common.app.career.dto.UniversityInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UniversityInfoDao {
    Page<UniversityInfo> findByName(String name, Pageable pageable);
}
