package com.via.account.infra.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash("refresh_token")
public class RefreshTokenRedisEntity {
    private String memberId;

    @Id
    private String token;
    private int useCount;
    private long expiresAtEpoch;

    @TimeToLive
    private long ttl;

    @Builder
    public RefreshTokenRedisEntity(String memberId, String token, int useCount, long expiresAtEpoch, long ttl) {
        this.memberId = memberId;
        this.token = token;
        this.useCount = useCount;
        this.expiresAtEpoch = expiresAtEpoch;
        this.ttl = ttl;
    }
}
