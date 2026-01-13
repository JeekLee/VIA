package com.via.content.api.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContentApiGroupConfig {
    @Bean
    public GroupedOpenApi contentApi() {
        return GroupedOpenApi.builder()
                .group("02-Content")
                .packagesToScan("com.via.content.api")
                .build();
    }
}
