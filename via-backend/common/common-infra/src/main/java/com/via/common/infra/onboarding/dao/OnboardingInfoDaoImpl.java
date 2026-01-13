package com.via.common.infra.onboarding.dao;

import com.via.common.app.onboarding.dao.OnboardingInfoDao;
import com.via.common.app.onboarding.dto.OnboardingInfo;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.via.common.infra.onboarding.entity.QOnboardingJpaEntity.onboardingJpaEntity;


@Repository
@RequiredArgsConstructor
public class OnboardingInfoDaoImpl implements OnboardingInfoDao {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<OnboardingInfo> findAll() {
        return jpaQueryFactory
                .select(Projections.constructor(OnboardingInfo.class,
                        onboardingJpaEntity.id,
                        onboardingJpaEntity.title,
                        onboardingJpaEntity.content,
                        onboardingJpaEntity.imagePath))
                .from(onboardingJpaEntity)
                .where(onboardingJpaEntity.activated.eq(true))
                .orderBy(onboardingJpaEntity.priority.asc())
                .fetch();
    }
}
