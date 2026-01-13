package com.via.account.infra.repository;

import com.via.account.infra.mapper.OAuth2AccountEntityMapper;
import com.via.account.infra.repository.jpa.JpaOAuth2AccountRepository;
import com.via.account.infra.entity.OAuth2AccountJpaEntity;
import com.via.account.app.repository.OAuth2AccountRepository;
import com.via.account.domain.enums.OAuth2Provider;
import com.via.account.domain.model.OAuth2Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OAuth2AccountRepositoryImpl implements OAuth2AccountRepository {
    private final JpaOAuth2AccountRepository jpaOAuth2AccountRepository;
    private final OAuth2AccountEntityMapper mapper;

    @Override
    public Optional<OAuth2Account> findByOAuth2ProviderAndEmail(OAuth2Provider provider, String email) {
        return jpaOAuth2AccountRepository.findByOauth2ProviderAndEmail(provider, email)
                .map(mapper::toDomain);
    }

    @Override
    public OAuth2Account save(OAuth2Account oAuth2Account) {
        OAuth2AccountJpaEntity entity = mapper.toEntity(oAuth2Account);
        OAuth2AccountJpaEntity saved = jpaOAuth2AccountRepository.save(entity);
        return mapper.toDomain(saved);
    }
}
