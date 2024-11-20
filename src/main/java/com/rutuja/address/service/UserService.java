package com.rutuja.address.service;

import com.rutuja.address.entity.UserEntity;
import com.rutuja.address.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@EnableWebFluxSecurity
public class UserService implements ReactiveUserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userRepository.findById(username).map(UserEntity::new);
    }
}