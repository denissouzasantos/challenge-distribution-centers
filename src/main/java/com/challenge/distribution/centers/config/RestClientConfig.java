package com.challenge.distribution.centers.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient RestClientConfig(RestClient.Builder builder) {
        var simpleClient = new SimpleClientHttpRequestFactory();
        simpleClient.setConnectTimeout(1000);
        simpleClient.setReadTimeout(1000);
        return builder
                .baseUrl("https://distributioncenters.free.beeceptor.com")
                .requestFactory(simpleClient)
                .build();
    }
}
