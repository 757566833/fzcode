package com.fzcode.cloud.gate.filter;

import lombok.SneakyThrows;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
public class TypeFilter implements Ordered, GlobalFilter {

    @SneakyThrows
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders httpHeaders = request.getHeaders();
        String auth = httpHeaders.getFirst(HttpHeaders.AUTHORIZATION);
        URI uri = request.getURI();
        if (uri.getPath().indexOf("/auth/login") == 0
                || uri.getPath().indexOf("/auth/register") == 0
                || uri.getPath().indexOf("/auth/forget") == 0
                || uri.getPath().indexOf("/mail/register") == 0
                || uri.getPath().indexOf("/mail/forget") == 0
                || uri.getPath().indexOf("/current") == 0
        ) {
            return chain.filter(exchange);
        } else if (request.getMethod() == HttpMethod.GET && !uri.getPath().contains("admin")) {
            return chain.filter(exchange);
        } else if (auth == null) {
            DataBuffer dataBuffer = exchange.getResponse().bufferFactory().wrap("token不存在".getBytes());
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().writeWith(Mono.just(dataBuffer));
        } else if (uri.getPath().indexOf("/pri/") > 0 && !auth.contains("basic ")) {
            DataBuffer dataBuffer = exchange.getResponse().bufferFactory().wrap("token类型不对".getBytes());
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().writeWith(Mono.just(dataBuffer));
        } else if (!auth.contains("bearer ")) {
            DataBuffer dataBuffer = exchange.getResponse().bufferFactory().wrap("token类型不对".getBytes());
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().writeWith(Mono.just(dataBuffer));
        } else {
            return chain.filter(exchange);
        }
    }

    @Override
    public int getOrder() {
        return -100;
    }
}
