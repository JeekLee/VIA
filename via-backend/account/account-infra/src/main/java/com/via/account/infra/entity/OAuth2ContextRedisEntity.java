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
@RedisHash("oauth2_context")
public class OAuth2ContextRedisEntity {
    @Id
    private String state;
    private String provider;
    private String codeVerifier;
    private String redirectPath;
    private String redirectUri;
    private long expiresAtEpoch;

    @TimeToLive
    private long ttl;

    @Builder
    public OAuth2ContextRedisEntity(String state, String provider, String codeVerifier, String redirectPath, String redirectUri, long expiresAtEpoch, long ttl) {
        this.state = state;
        this.provider = provider;
        this.codeVerifier = codeVerifier;
        this.redirectPath = redirectPath;
        this.redirectUri = redirectUri;
        this.expiresAtEpoch = expiresAtEpoch;
        this.ttl = ttl;
    }
}
