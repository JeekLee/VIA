package com.via.common.crawler.config;

import feign.RequestInterceptor;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.via.common.crawler")
public class CommonCrawlerFeignConfig {
    private final static String TMP = "temp";

    @Bean
    public RequestInterceptor commonContentLengthInterceptor() {
        return requestTemplate -> {
            if (requestTemplate.body() == null && "POST".equals(requestTemplate.method())) {
                requestTemplate.body(TMP);
                requestTemplate.header("Content-Length", String.valueOf(TMP.getBytes().length));
            }
        };
    }
}
