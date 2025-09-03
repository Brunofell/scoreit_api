package com.scoreit.scoreit.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;


@Configuration
public class ResendClientConfig {

    @Value("${resend.api.key}")
    private String apiKey;

    @Value("${resend.api.url:https://api.resend.com}")
    private String baseUrl;

    @Bean
    public RestClient resendClient() {
        return RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .defaultHeader("Accept", "application/json")
                .build();
    }
}
