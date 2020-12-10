package com.fzcode.gateservice.filter;

import com.fzcode.gateservice.config.Services;
import com.fzcode.gateservice.flow.AuthorityFlow;
import com.fzcode.gateservice.util.TokenUtils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
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
public class AuthFilter implements Ordered, GlobalFilter {

    Services services;

    @Autowired
    public void setConfig(Services services) {
        this.services = services;
    }

    AuthorityFlow authorityFlow;

    @Autowired
    public void setAuthorityFlow(AuthorityFlow authorityFlow) {
        this.authorityFlow = authorityFlow;
    }

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
        } else if (request.getMethod() == HttpMethod.GET && uri.getPath().indexOf("admin") < 0) {
            return chain.filter(exchange);
        } else if (auth.indexOf("basic") == 0) {
            String[] infoArray = auth.split(":");
            String serviceName = infoArray[0];
            String password = infoArray[1];
            if (services.getAuth().get(serviceName) != password) {
                DataBuffer dataBuffer = exchange.getResponse().bufferFactory().wrap("token不对".getBytes());
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().writeWith(Mono.just(dataBuffer));
            } else {
                return chain.filter(exchange);
            }
        } else {
            String email = null;
            try {
                email = TokenUtils.parseBearer(auth);
            } catch (Exception e) {
                DataBuffer dataBuffer = exchange.getResponse().bufferFactory().wrap("token不存在".getBytes());
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().writeWith(Mono.just(dataBuffer));
            }
            if (email == null) {
                DataBuffer dataBuffer = exchange.getResponse().bufferFactory().wrap("token不对".getBytes());
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().writeWith(Mono.just(dataBuffer));
            } else {
                String finalEmail = email;
                return authorityFlow.getAuthority(email).flatMap(authority -> {
                    if (uri.getPath().indexOf("admin") > 0 && !authority.equals("ADMIN")) {
                        DataBuffer dataBuffer = exchange.getResponse().bufferFactory().wrap("没权限".getBytes());
                        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                        return exchange.getResponse().writeWith(Mono.just(dataBuffer));
                    }
                    ServerHttpRequest nextRequest = request
                            .mutate()
                            .header("email", finalEmail)
                            .header("userAuthority", authority)
                            .build();
                    ServerWebExchange nextExchange = exchange.mutate().request(nextRequest).build();
                    return chain.filter(nextExchange);
                });
            }
        }
    }

    @Override
    public int getOrder() {
        return 10;
    }
}
