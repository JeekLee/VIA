package com.via.account.infra.mapper;

import com.via.account.infra.entity.OAuth2ContextRedisEntity;
import com.via.core.utils.EpochConverter;
import com.via.account.domain.enums.OAuth2Provider;
import com.via.account.domain.model.OAuth2Context;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

@Component
public class OAuth2ContextEntityMapper {

    public OAuth2Context toDomain(OAuth2ContextRedisEntity entity) {
        return OAuth2Context.builderWithExpiresAt()
                .state(entity.getState())
                .provider(OAuth2Provider.valueOf(entity.getProvider()))
                .codeVerifier(entity.getCodeVerifier())
                .redirectPath(entity.getRedirectPath())
                .redirectUri(entity.getRedirectUri())
                .expiresAt(EpochConverter.convertEpochToDateTime(entity.getExpiresAtEpoch()))
                .buildWithExpiresAt();
    }

    public OAuth2ContextRedisEntity toEntity(OAuth2Context domain) {
        LocalDateTime nowKst = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        long ttlSeconds = ChronoUnit.SECONDS.between(nowKst, domain.getExpiresAt());
        long expiresAtEpoch = domain.getExpiresAt().atZone(ZoneId.of("Asia/Seoul")).toEpochSecond();

        return OAuth2ContextRedisEntity.builder()
                .state(domain.getState())
                .provider(domain.getProvider().name())
                .codeVerifier(domain.getCodeVerifier())
                .redirectPath(domain.getRedirectPath())
                .redirectUri(domain.getRedirectUri())
                .expiresAtEpoch(expiresAtEpoch)
                .ttl(Math.max(ttlSeconds, 1))
                .build();
    }
}
