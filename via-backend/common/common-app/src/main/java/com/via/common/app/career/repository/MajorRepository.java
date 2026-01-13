package com.via.common.app.career.repository;

import com.via.common.domain.career.model.Major;

import java.util.List;

public interface MajorRepository {
    void saveAll(List<Major> majors);
}
