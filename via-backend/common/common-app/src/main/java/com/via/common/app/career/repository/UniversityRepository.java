package com.via.common.app.career.repository;

import com.via.common.domain.career.model.University;

import java.util.List;

public interface UniversityRepository {
    void saveAll(List<University> universities);
}
