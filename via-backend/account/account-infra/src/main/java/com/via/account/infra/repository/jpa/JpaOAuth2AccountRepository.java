package com.via.account.infra.repository.jpa;

import com.via.account.domain.enums.OAuth2Provider;
import com.via.account.infra.entity.OAuth2AccountJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaOAuth2AccountRepository extends JpaRepository<OAuth2AccountJpaEntity, Long> {
    Optional<OAuth2AccountJpaEntity> findByOauth2ProviderAndEmail(OAuth2Provider provider, String email);
}
