package com.via.account.infra.mapper;

import com.via.account.infra.entity.RefreshTokenRedisEntity;
import com.via.core.utils.EpochConverter;
import com.via.account.domain.model.RefreshToken;
import com.via.account.domain.model.id.MemberId;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

@Component
public class RefreshTokenEntityMapper {

    public RefreshToken toDomain(RefreshTokenRedisEntity entity) {
        return RefreshToken.builder()
                .memberId(new MemberId(Long.parseLong(entity.getMemberId())))
                .token(entity.getToken())
                .useCount(entity.getUseCount())
                .expiresAt(EpochConverter.convertEpochToDateTime(entity.getExpiresAtEpoch()))
                .build();
    }

    public RefreshTokenRedisEntity toEntity(RefreshToken domain) {
        LocalDateTime nowKst = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        long ttlSeconds = ChronoUnit.SECONDS.between(nowKst, domain.expiresAt());
        long expiresAtEpoch = domain.expiresAt().atZone(ZoneId.of("Asia/Seoul")).toEpochSecond();

        return RefreshTokenRedisEntity.builder()
                .memberId(String.valueOf(domain.memberId().id()))
                .token(domain.token())
                .useCount(domain.useCount())
                .expiresAtEpoch(expiresAtEpoch)
                .ttl(Math.max(ttlSeconds, 1))
                .build();
    }
}
