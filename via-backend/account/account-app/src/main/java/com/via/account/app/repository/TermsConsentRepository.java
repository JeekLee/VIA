package com.via.account.app.repository;



import com.via.account.domain.model.TermsConsent;
import com.via.account.domain.model.id.MemberId;
import com.via.account.domain.model.id.TermsId;

import java.util.List;

public interface TermsConsentRepository {
    List<TermsConsent> findAllByMemberIdAndWithdrawnFalse(MemberId memberId);
    List<TermsConsent> findAllByMemberIdAndTermsIdListAndWithdrawnFalse(MemberId memberId, List<TermsId> termsIdList);
    TermsConsent save(TermsConsent termsConsent);
    void saveAll(List<TermsConsent> termsConsentList);
}
