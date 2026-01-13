package com.via.account.infra.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories(basePackages = "com.via.account.infra.repository.redis")
public class AccountRedisConfig {
}
