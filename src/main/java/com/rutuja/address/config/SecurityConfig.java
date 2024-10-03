package com.rutuja.address.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
   @Bean
    public MapReactiveUserDetailsService userDetailsService(){
        UserDetails user= User.withDefaultPasswordEncoder()
                .username("addressRoot")
                .password("addressRoot")
                .roles("addressRoot")
                .build();
        return new MapReactiveUserDetailsService(user);
    }
}
