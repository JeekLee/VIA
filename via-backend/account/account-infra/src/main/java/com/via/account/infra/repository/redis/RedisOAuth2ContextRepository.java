package com.via.account.infra.repository.redis;

import com.via.account.infra.entity.OAuth2ContextRedisEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisOAuth2ContextRepository extends CrudRepository<OAuth2ContextRedisEntity, String> {

}
