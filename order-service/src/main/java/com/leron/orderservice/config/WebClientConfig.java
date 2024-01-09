package com.leron.orderservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    //We Need Spring WebFlux to use WebClient

    @Bean
    public WebClient webClient(){
        return WebClient.builder().build();
    }
}
