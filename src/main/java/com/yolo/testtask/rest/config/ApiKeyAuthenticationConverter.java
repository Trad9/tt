package com.yolo.testtask.rest.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class ApiKeyAuthenticationConverter implements ServerAuthenticationConverter {

    @Value("${tt.http.auth-token.header}")
    private String API_KEY_HEADER;

    @Value("${tt.http.auth-token.value}")
    private String API_KEY_VALUE;

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        return Mono.justOrEmpty(exchange)
                .flatMap(serverWebExchange -> Mono.justOrEmpty(serverWebExchange.getRequest().getHeaders().get(API_KEY_HEADER)))
                .filter(headerValues -> !headerValues.isEmpty())
                .flatMap(headerValues -> check(headerValues.get(0)));
    }

    private Mono<ApiKeyAuthentication> check(final String apiKey) {
        ApiKeyAuthentication auth =
        API_KEY_VALUE.equals(apiKey)? new ApiKeyAuthentication(apiKey, "user") : null;
        return Mono.justOrEmpty(auth);
    }
}
