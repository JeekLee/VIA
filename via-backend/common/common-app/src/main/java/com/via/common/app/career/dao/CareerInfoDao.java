package com.via.common.app.career.dao;

import com.via.common.app.career.dto.CareerInfo;
import com.via.common.domain.career.model.id.CareerOwnerId;

import java.util.Optional;

public interface CareerInfoDao {
    Optional<CareerInfo> findByMemberId(CareerOwnerId careerOwnerId);
}
