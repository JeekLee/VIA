package com.via.account.infra.repository.redis;

import com.via.account.infra.entity.RefreshTokenRedisEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisRefreshTokenRepository extends CrudRepository<RefreshTokenRedisEntity, String> {

}
