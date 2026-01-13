package com.via.account.infra.repository;

import com.via.account.infra.entity.OAuth2ContextRedisEntity;
import com.via.account.infra.mapper.OAuth2ContextEntityMapper;
import com.via.account.infra.repository.redis.RedisOAuth2ContextRepository;
import com.via.account.app.repository.OAuth2ContextRepository;
import com.via.account.domain.model.OAuth2Context;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OAuth2ContextRepositoryImpl implements OAuth2ContextRepository {
    private final RedisOAuth2ContextRepository redisOAuth2ContextRepository;
    private final OAuth2ContextEntityMapper mapper;

    @Override
    public OAuth2Context save(OAuth2Context oAuth2Context) {
        OAuth2ContextRedisEntity entity = mapper.toEntity(oAuth2Context);
        OAuth2ContextRedisEntity saved = redisOAuth2ContextRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<OAuth2Context> findByState(String state) {
        return redisOAuth2ContextRepository.findById(state)
                .map(mapper::toDomain);
    }
}
