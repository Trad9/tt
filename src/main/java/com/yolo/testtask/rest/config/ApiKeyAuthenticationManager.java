package com.yolo.testtask.rest.config;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ApiKeyAuthenticationManager implements ReactiveAuthenticationManager {

        @Override
        public Mono<Authentication> authenticate(Authentication authentication) {
            return Mono.fromSupplier(() -> {
                if (authentication != null && authentication.getCredentials() != null) {
                    authentication.setAuthenticated(true);
                }
                return authentication;
            });
        }

}
