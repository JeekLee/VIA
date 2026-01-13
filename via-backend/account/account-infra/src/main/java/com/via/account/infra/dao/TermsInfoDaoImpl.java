package com.via.account.infra.dao;

import com.via.account.app.dao.TermsInfoDao;
import com.via.account.app.dto.TermsInfo;
import com.via.account.infra.entity.TermsCategoryJpaEntity;
import com.via.account.infra.entity.TermsJpaEntity;
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
public class TermsInfoDaoImpl implements TermsInfoDao {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<TermsInfo> findAllLatestTerms() {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

        List<TermsCategoryJpaEntity> categories = jpaQueryFactory
                .selectFrom(termsCategoryJpaEntity)
                .orderBy(termsCategoryJpaEntity.priority.asc())
                .fetch();

        List<TermsInfo> result = new ArrayList<>();

        for (TermsCategoryJpaEntity category : categories) {
            TermsJpaEntity latestTerms = jpaQueryFactory
                    .selectFrom(termsJpaEntity)
                    .where(
                            termsJpaEntity.termsCategoryId.eq(category.getId())
                                    .and(termsJpaEntity.effectiveAt.loe(now))
                    )
                    .orderBy(
                            termsJpaEntity.majorVersion.desc(),
                            termsJpaEntity.minorVersion.desc()
                    )
                    .fetchFirst();

            if (latestTerms != null) {
                TermsInfo termsInfo = TermsInfo.builder()
                        .termsId(latestTerms.getId())
                        .required(category.isRequired())
                        .majorVersion(latestTerms.getMajorVersion())
                        .minorVersion(latestTerms.getMinorVersion())
                        .title(latestTerms.getTitle())
                        .content(latestTerms.getContent())
                        .effectiveAt(latestTerms.getEffectiveAt())
                        .build();

                result.add(termsInfo);
            }
        }

        return result;
    }
}
