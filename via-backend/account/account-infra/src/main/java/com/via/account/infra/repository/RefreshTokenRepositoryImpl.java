package com.via.account.infra.repository;

import com.via.account.infra.mapper.RefreshTokenEntityMapper;
import com.via.account.infra.repository.redis.RedisRefreshTokenRepository;
import com.via.account.infra.entity.RefreshTokenRedisEntity;
import com.via.account.app.repository.RefreshTokenRepository;
import com.via.account.domain.model.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {
    private final RedisRefreshTokenRepository redisRefreshTokenRepository;
    private final RefreshTokenEntityMapper mapper;

    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        RefreshTokenRedisEntity entity = mapper.toEntity(refreshToken);
        RefreshTokenRedisEntity saved = redisRefreshTokenRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public void deleteByToken(String token) {
        redisRefreshTokenRepository.deleteById(String.valueOf(token));
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return redisRefreshTokenRepository.findById(token).map(mapper::toDomain);
    }
}
