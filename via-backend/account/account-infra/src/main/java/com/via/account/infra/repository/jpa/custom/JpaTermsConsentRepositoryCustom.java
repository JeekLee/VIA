package com.via.account.infra.repository.jpa.custom;


import com.via.account.infra.entity.TermsConsentJpaEntity;

import java.util.List;

public interface JpaTermsConsentRepositoryCustom {
    List<TermsConsentJpaEntity> findAllByMemberIdAndTermsIdListAndWithdrawnFalse(Long memberId, List<Long> termsId);
}
