package com.via.common.api.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonApiGroupConfig {
    @Bean
    public GroupedOpenApi commonApi() {
        return GroupedOpenApi.builder()
                .group("99-Common")
                .packagesToScan("com.via.common.api")
                .build();
    }
}
