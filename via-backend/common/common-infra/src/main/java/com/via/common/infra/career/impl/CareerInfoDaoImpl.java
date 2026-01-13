package com.via.common.infra.career.impl;

import com.via.common.app.career.dao.CareerInfoDao;
import com.via.common.app.career.dto.CareerInfo;
import com.via.common.domain.career.model.id.CareerOwnerId;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.via.common.infra.career.database.entity.QCareerJpaEntity.careerJpaEntity;

@Repository
@RequiredArgsConstructor
public class CareerInfoDaoImpl implements CareerInfoDao {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<CareerInfo> findByMemberId(CareerOwnerId careerOwnerId) {
        CareerInfo result = jpaQueryFactory
                .select(Projections.constructor(
                        CareerInfo.class,
                        careerJpaEntity.memberId,
                        careerJpaEntity.imagePath,
                        careerJpaEntity.name,
                        careerJpaEntity.birthDate,
                        careerJpaEntity.gender,
                        careerJpaEntity.phone,
                        careerJpaEntity.email,
                        careerJpaEntity.zipCode,
                        careerJpaEntity.road,
                        careerJpaEntity.detail
                ))
                .from(careerJpaEntity)
                .where(careerJpaEntity.memberId.eq(careerOwnerId.id()))
                .fetchOne();

        return Optional.ofNullable(result);
    }
}
