package com.via.common.infra.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EntityScan(basePackages = "com.via.common.infra")
@EnableJpaRepositories(basePackages = "com.via.common.infra")
public class CommonJpaConfig {

}
