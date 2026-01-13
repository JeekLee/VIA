package com.via.account.infra.mapper;

import com.via.account.infra.entity.OAuth2AccountJpaEntity;
import com.via.account.domain.model.OAuth2Account;
import com.via.account.domain.model.id.MemberId;
import com.via.account.domain.model.id.OAuth2AccountId;
import org.springframework.stereotype.Component;

@Component
public class OAuth2AccountEntityMapper {

    public OAuth2Account toDomain(OAuth2AccountJpaEntity entity) {
        return OAuth2Account.builder()
                .oAuth2AccountId(new OAuth2AccountId(entity.getId()))
                .memberId(new MemberId(entity.getMemberId()))
                .provider(entity.getOauth2Provider())
                .email(entity.getEmail())
                .initialAccount(entity.getInitialAccount())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public OAuth2AccountJpaEntity toEntity(OAuth2Account domain) {
        return OAuth2AccountJpaEntity.builder()
                .id(domain.oAuth2AccountId() != null ? domain.oAuth2AccountId().id() : null)
                .memberId(domain.memberId().id())
                .oauth2Provider(domain.provider())
                .email(domain.email())
                .initialAccount(domain.initialAccount())
                .createdAt(domain.createdAt())
                .updatedAt(domain.updatedAt())
                .build();
    }
}
