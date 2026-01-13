package com.via.common.app.career.dao;

import com.via.common.app.career.dto.MajorInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MajorInfoDao {
    Page<MajorInfo> findByName(String name, Pageable pageable);
}
