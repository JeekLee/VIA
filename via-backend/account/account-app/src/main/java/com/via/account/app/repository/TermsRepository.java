package com.via.account.app.repository;



import com.via.account.domain.model.Terms;

import java.util.List;

public interface TermsRepository {
    List<Terms> findAllConsentRequired();
}
