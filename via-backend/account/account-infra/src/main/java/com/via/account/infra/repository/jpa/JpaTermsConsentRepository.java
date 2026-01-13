package com.via.account.infra.repository.jpa;

import com.via.account.infra.entity.TermsConsentJpaEntity;
import com.via.account.infra.repository.jpa.custom.JpaTermsConsentRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaTermsConsentRepository extends JpaRepository<TermsConsentJpaEntity, Long>, JpaTermsConsentRepositoryCustom {
    List<TermsConsentJpaEntity> findAllByMemberIdAndWithdrawnFalse(Long memberId);
}
