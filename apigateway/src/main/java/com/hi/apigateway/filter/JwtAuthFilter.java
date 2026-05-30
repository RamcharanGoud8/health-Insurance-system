package com.hi.apigateway.filter;


import com.hi.apigateway.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class JwtAuthFilter implements GlobalFilter, Ordered {

    @Autowired
    private JwtUtil jwtUtil;

    // These paths dont need token
    private static final List<String> PUBLIC_PATHS = List.of(
            "/auth/register",
            "/auth/login"
    );

    @Override
    public Mono<Void> filter(
            ServerWebExchange exchange,
            GatewayFilterChain chain) {

        String path = exchange.getRequest()
                .getURI().getPath();

        // Skip token check for public paths
        if (isPublicPath(path)) {
            return chain.filter(exchange);
        }

        // Check Authorization header
        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst("Authorization");

        // No token provided
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            exchange.getResponse()
                    .setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // Extract token
        String token = authHeader.substring(7);

        // Validate token
        if (!jwtUtil.validateToken(token)) {
            exchange.getResponse()
                    .setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // Token valid - forward request
        return chain.filter(exchange);
    }

    private boolean isPublicPath(String path) {
        return PUBLIC_PATHS.stream()
                .anyMatch(path::startsWith);
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
