package com.via.content.infra.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EntityScan(basePackages = "com.via.content.infra")
@EnableJpaRepositories(basePackages = "com.via.content.infra")
public class ContentJpaConfig {

}
