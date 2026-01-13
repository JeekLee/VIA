package com.via.account.infra.repository;

import com.via.account.app.repository.TermsRepository;
import com.via.account.domain.model.Terms;
import com.via.account.infra.mapper.TermsEntityMapper;
import com.via.account.infra.repository.jpa.JpaTermsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TermsRepositoryImpl implements TermsRepository {
    private final JpaTermsRepository jpaTermsRepository;
    private final TermsEntityMapper termsEntityMapper;

    @Override
    public List<Terms> findAllConsentRequired() {
        return jpaTermsRepository.findAllConsentRequired()
                .stream()
                .map(termsEntityMapper::toDomain)
                .toList();
    }
}