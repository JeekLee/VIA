package com.via.account.infra.repository.jpa;

import com.via.account.infra.entity.TermsJpaEntity;
import com.via.account.infra.repository.jpa.custom.JpaTermsRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTermsRepository extends JpaRepository<TermsJpaEntity, Long>, JpaTermsRepositoryCustom {
}
