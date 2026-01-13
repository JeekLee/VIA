package com.via.account.infra.repository;

import com.via.account.app.repository.TermsConsentRepository;
import com.via.account.domain.model.TermsConsent;
import com.via.account.domain.model.id.MemberId;
import com.via.account.domain.model.id.TermsId;
import com.via.account.infra.mapper.TermsConsentEntityMapper;
import com.via.account.infra.repository.jpa.JpaTermsConsentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TermsConsentRepositoryImpl implements TermsConsentRepository {
    private final JpaTermsConsentRepository jpaTermsConsentRepository;
    private final TermsConsentEntityMapper termsConsentEntityMapper;

    @Override
    public List<TermsConsent> findAllByMemberIdAndWithdrawnFalse(MemberId memberId) {
        return jpaTermsConsentRepository.findAllByMemberIdAndWithdrawnFalse(memberId.id())
                .stream()
                .map(termsConsentEntityMapper::toDomain)
                .toList();
    }

    @Override
    public List<TermsConsent> findAllByMemberIdAndTermsIdListAndWithdrawnFalse(MemberId memberId, List<TermsId> termsIdList) {
        return jpaTermsConsentRepository.findAllByMemberIdAndTermsIdListAndWithdrawnFalse(
                        memberId.id(),
                        termsIdList.stream().map(TermsId::id).toList()
                )
                .stream()
                .map(termsConsentEntityMapper::toDomain)
                .toList();
    }

    @Override
    public TermsConsent save(TermsConsent termsConsent) {
        return termsConsentEntityMapper.toDomain(
                jpaTermsConsentRepository.save(
                        TermsConsentEntityMapper.fromDomain(termsConsent)
                )
        );
    }

    @Override
    public void saveAll(List<TermsConsent> termsConsentList) {
        jpaTermsConsentRepository.saveAll(
                termsConsentList.stream()
                        .map(TermsConsentEntityMapper::fromDomain)
                        .toList()
        );
    }
}