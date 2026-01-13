package com.via.account.infra.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EntityScan(basePackages = "com.via.account.infra.entity")
@EnableJpaRepositories(basePackages = "com.via.account.infra.repository.jpa")
public class AccountJpaConfig {

}
