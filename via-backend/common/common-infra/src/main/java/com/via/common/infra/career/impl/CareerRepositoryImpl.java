package com.via.common.infra.career.impl;

import com.via.common.app.career.repository.CareerRepository;
import com.via.common.domain.career.model.Career;
import com.via.common.domain.career.model.id.CareerOwnerId;
import com.via.common.infra.career.database.entity.CareerJpaEntity;
import com.via.common.infra.career.mapper.CareerMapper;
import com.via.common.infra.career.database.repository.jpa.CareerJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CareerRepositoryImpl implements CareerRepository {
    private final CareerJpaRepository careerJpaRepository;
    private final CareerMapper mapper;

    @Override
    public Career save(Career career) {
        CareerJpaEntity entity = mapper.fromDomain(career);
        CareerJpaEntity saved = careerJpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Career> findByMemberId(CareerOwnerId careerOwnerId) {
        return careerJpaRepository.findById(careerOwnerId.id()).map(mapper::toDomain);
    }
}
