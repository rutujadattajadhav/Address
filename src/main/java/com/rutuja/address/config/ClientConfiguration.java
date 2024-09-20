package com.rutuja.address.config;

import io.micrometer.observation.ObservationRegistry;
import jakarta.annotation.PostConstruct;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Hooks;

@Configuration
public class ClientConfiguration {



    @Bean("webClientBuilder")
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder().observationRegistry(ObservationRegistry.NOOP);
    }

    @PostConstruct
    void configureLifeCycleHooks() {
        Hooks.enableAutomaticContextPropagation();
    }
}
