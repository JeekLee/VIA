package com.via.account.api.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccountApiGroupConfig {
    @Bean
    public GroupedOpenApi accountApi() {
        return GroupedOpenApi.builder()
                .group("01-Account")
                .packagesToScan("com.via.account.api")
                .build();
    }
}
