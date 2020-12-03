package com.fzcode.gateservice.filter;

import com.fzcode.gateservice.flow.AuthorityFlow;
import com.fzcode.gateservice.util.JwtUtils;
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

    AuthorityFlow authorityFlow;

    @Autowired
    public void setAuthorityFlow(AuthorityFlow authorityFlow) {
        this.authorityFlow = authorityFlow;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders httpHeaders = request.getHeaders();
        String auth = httpHeaders.getFirst(HttpHeaders.AUTHORIZATION);
        URI uri = request.getURI();
        // 这里是不需要token 的路由
        if (uri.getPath().indexOf("/auth/login") == 0
                || uri.getPath().indexOf("/auth/pub/account/register") == 0
                || uri.getPath().indexOf("/auth/pub/account/forget") == 0
                || uri.getPath().indexOf("/mail/pub/email/register") == 0
                || uri.getPath().indexOf("/mail/pub/email/forget") == 0
                || uri.getPath().indexOf("/current") == 0
        ) {
            return chain.filter(exchange);
        } else if (request.getMethod() == HttpMethod.GET) {
            return chain.filter(exchange);
        } else if (auth == null) {
            DataBuffer dataBuffer = exchange.getResponse().bufferFactory().wrap("token不存在".getBytes());
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().writeWith(Mono.just(dataBuffer));
        } else {
            String email = null;
            System.out.println("auth:" + auth);
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
                String finalEmail = email;
                return authorityFlow.saveAuthFromSqlToRedis(email).flatMap(authority->{
                    ServerHttpRequest nextRequest = request
                            .mutate()
                            .header("email", finalEmail)
                            .header("userAuthority", authority)
                            .build();
                    ServerWebExchange nextExchange = exchange.mutate().request(nextRequest).build();
                    return chain.filter(nextExchange);
                });
//                ServerHttpRequest nextRequest = request
//                        .mutate()
//                        .header("email", email)
//                        .build();
//                ServerWebExchange nextExchange = exchange.mutate().request(nextRequest).build();
//                httpHeaders.set("email", email);
//                return chain.filter(nextExchange);
            }
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
