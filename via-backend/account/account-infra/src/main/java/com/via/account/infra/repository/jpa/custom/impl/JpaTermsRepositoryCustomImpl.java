package com.via.account.infra.repository.jpa.custom.impl;

import com.via.account.infra.entity.TermsJpaEntity;
import com.via.account.infra.repository.jpa.custom.JpaTermsRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static com.via.account.infra.entity.QTermsCategoryJpaEntity.termsCategoryJpaEntity;
import static com.via.account.infra.entity.QTermsJpaEntity.termsJpaEntity;

@Repository
@RequiredArgsConstructor
public class JpaTermsRepositoryCustomImpl implements JpaTermsRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<TermsJpaEntity> findAllConsentRequired() {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

        List<Long> requiredCategoryIds = jpaQueryFactory
                .select(termsCategoryJpaEntity.id)
                .from(termsCategoryJpaEntity)
                .where(termsCategoryJpaEntity.required.isTrue())
                .orderBy(termsCategoryJpaEntity.priority.asc())
                .fetch();

        List<TermsJpaEntity> result = new ArrayList<>();
        for (Long termsCategoryId : requiredCategoryIds) {
            TermsJpaEntity latestTerms = jpaQueryFactory
                    .selectFrom(termsJpaEntity)
                    .where(termsJpaEntity.termsCategoryId.eq(termsCategoryId)
                            .and(termsJpaEntity.effectiveAt.loe(now)))
                    .orderBy(
                            termsJpaEntity.majorVersion.desc(),
                            termsJpaEntity.minorVersion.desc()
                    )
                    .fetchFirst();

            if (latestTerms != null) result.add(latestTerms);
        }

        return result;
    }
}
