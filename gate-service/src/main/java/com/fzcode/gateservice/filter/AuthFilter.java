package com.fzcode.gateservice.filter;

import com.fzcode.gateservice.dto.common.TokenDTO;
import com.fzcode.gateservice.util.JwtUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
public class AuthFilter implements Ordered, GlobalFilter {
    private WebClient client = WebClient.create();
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders httpHeaders = request.getHeaders();
        String auth = httpHeaders.getFirst(HttpHeaders.AUTHORIZATION);
        URI uri = request.getURI();
        // 这里是不需要token 的路由
        if (uri.getPath().indexOf("/auth/login") == 0
                || uri.getPath().indexOf("/auth/register") == 0
                || uri.getPath().indexOf("/auth/forget") == 0
                || uri.getPath().indexOf("/mail/register") == 0
                || uri.getPath().indexOf("/mail/forget") == 0
        ) {
            return chain.filter(exchange);
        } else if (auth == null) {
            DataBuffer dataBuffer = exchange.getResponse().bufferFactory().wrap("token不存在".getBytes());
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().writeWith(Mono.just(dataBuffer));
        } else {
            String email = null;
            try {
                email = JwtUtils.parseToken(auth);
            } catch (Exception e) {
                DataBuffer dataBuffer = exchange.getResponse().bufferFactory().wrap("token不存在".getBytes());
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().writeWith(Mono.just(dataBuffer));
            }
            System.out.println(email);
            if (email == null) {
                DataBuffer dataBuffer = exchange.getResponse().bufferFactory().wrap("xiake~!!".getBytes());
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().writeWith(Mono.just(dataBuffer));
            } else {
                httpHeaders.set("email", email);
            }
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
