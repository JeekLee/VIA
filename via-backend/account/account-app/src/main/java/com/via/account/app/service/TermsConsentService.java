package com.via.account.app.service;

import com.via.account.app.repository.TermsConsentRepository;
import com.via.account.domain.model.TermsConsent;
import com.via.account.domain.model.id.MemberId;
import com.via.account.domain.model.id.TermsId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TermsConsentService {
    private final TermsConsentRepository termsConsentRepository;

    @Transactional
    public void agree(MemberId memberId, List<Long> termsIds) {
        List<TermsId> termsIdList = termsIds.stream().map(TermsId::new).toList();
        List<TermsConsent> newConsents = termsIdList.stream()
                .map(termsId -> TermsConsent.create(memberId, termsId))
                .toList();

        termsConsentRepository.saveAll(newConsents);
    }

    @Transactional
    public void withdraw(MemberId memberId, List<Long> termsIds) {
        List<TermsId> termsIdList = termsIds.stream().map(TermsId::new).toList();
        List<TermsConsent> consents = termsConsentRepository.findAllByMemberIdAndTermsIdListAndWithdrawnFalse(memberId, termsIdList);
        termsConsentRepository.saveAll(consents.stream().map(TermsConsent::withdraw).toList());
    }
}
