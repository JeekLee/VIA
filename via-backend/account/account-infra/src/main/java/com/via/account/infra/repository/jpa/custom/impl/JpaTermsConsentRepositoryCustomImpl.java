package com.via.account.infra.repository.jpa.custom.impl;

import com.via.account.infra.repository.jpa.custom.JpaTermsConsentRepositoryCustom;
import com.via.account.infra.entity.TermsConsentJpaEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.via.account.infra.entity.QTermsConsentJpaEntity.termsConsentJpaEntity;

@Repository
@RequiredArgsConstructor
public class JpaTermsConsentRepositoryCustomImpl implements JpaTermsConsentRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<TermsConsentJpaEntity> findAllByMemberIdAndTermsIdListAndWithdrawnFalse(Long memberId, List<Long> termsIdList) {
        return jpaQueryFactory
                .selectFrom(termsConsentJpaEntity)
                .where(
                        termsConsentJpaEntity.memberId.eq(memberId),
                        termsConsentJpaEntity.termsId.in(termsIdList),
                        termsConsentJpaEntity.withdrawn.eq(false)
                )
                .fetch();
    }
}
