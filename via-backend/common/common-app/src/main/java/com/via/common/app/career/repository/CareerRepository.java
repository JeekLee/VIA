package com.via.common.app.career.repository;

import com.via.common.domain.career.model.Career;
import com.via.common.domain.career.model.id.CareerOwnerId;

import java.util.Optional;

public interface CareerRepository {
    Career save(Career career);

    Optional<Career> findByMemberId(CareerOwnerId careerOwnerId);
}
