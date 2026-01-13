package com.via.account.infra.repository.jpa.custom;


import com.via.account.infra.entity.TermsJpaEntity;

import java.util.List;

public interface JpaTermsRepositoryCustom {
    List<TermsJpaEntity> findAllConsentRequired();
}
