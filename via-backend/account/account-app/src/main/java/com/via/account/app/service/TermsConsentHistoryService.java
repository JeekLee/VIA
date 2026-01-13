package com.via.account.app.service;

import com.via.account.app.repository.TermsConsentRepository;
import com.via.account.app.repository.TermsRepository;
import com.via.account.domain.model.Terms;
import com.via.account.domain.model.TermsConsent;
import com.via.account.domain.model.id.MemberId;
import com.via.account.domain.model.id.TermsId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TermsConsentHistoryService {
    private final TermsRepository termsRepository;
    private final TermsConsentRepository termsConsentRepository;

    @Transactional(readOnly = true)
    public boolean checkRequiredTermsConsent(MemberId memberId) {
        List<Terms> requiredTerms = termsRepository.findAllConsentRequired();
        List<TermsConsent> consentedTerms = termsConsentRepository.findAllByMemberIdAndWithdrawnFalse(memberId);

        Set<TermsId> consentedTermsIds = consentedTerms.stream()
                .map(TermsConsent::termsId)
                .collect(Collectors.toSet());

        return requiredTerms.stream().allMatch(terms -> consentedTermsIds.contains(terms.termsId()));
    }
}
