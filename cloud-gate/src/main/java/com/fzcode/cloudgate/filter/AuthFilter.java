package com.fzcode.cloudgate.filter;

import com.fzcode.cloudgate.config.Services;
import com.fzcode.cloudgate.service.AuthorityService;
import com.fzcode.cloudgate.util.TokenUtils;
import com.fzcode.internalcommon.dto.common.TokenInfoDTO;
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
import org.springframework.http.server.reactive.ServerHttpResponse;
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

    AuthorityService authorityService;

    @Autowired
    public void setAuthorityFlow(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    TokenUtils tokenUtils;
    @Autowired
    public void  setTokenUtils(TokenUtils tokenUtils){
        this.tokenUtils= tokenUtils;
    }
    @SneakyThrows
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders httpHeaders = request.getHeaders();
        String auth = httpHeaders.getFirst(HttpHeaders.AUTHORIZATION);
        URI uri = request.getURI();
        // 不需要token的接口
        if (uri.getPath().indexOf("/auth/login") >= 0
                || uri.getPath().indexOf("/auth/register") >= 0
                || uri.getPath().indexOf("/auth/forget") >= 0
                || uri.getPath().indexOf("/auth/github") >= 0
                || uri.getPath().indexOf("/auth/oauth") >= 0
                || uri.getPath().indexOf("/mail/register") >= 0
                || uri.getPath().indexOf("/mail/forget") >= 0
        ) {
            return chain.filter(exchange);
        }
        // 测试接口
        else if(uri.getPath().indexOf("test") > 0){
            return chain.filter(exchange);
        }
        // todo 有一部分查询，是不需要token的
        else if(request.getMethod() == HttpMethod.GET &&(
                uri.getPath().indexOf("/note/") >= 0||
                uri.getPath().indexOf("/text/") >= 0||
                uri.getPath().indexOf("/category/") >= 0)&&
                (uri.getPath().indexOf("/self/")<0)
        ){

            return chain.filter(exchange);
        }
        // admin 管理员相关的接口
        else if (uri.getPath().indexOf("admin") >= 0) {
            String email = null;
            String aid = null;
            String uid = null;
            // 解析token
            try {
                TokenInfoDTO tokenInfoDTO = this.tokenUtils.parseBearer(auth);
                email = tokenInfoDTO.getEmail();
                aid = tokenInfoDTO.getAid();
                uid = tokenInfoDTO.getUid();

            } catch (Exception e) {
                System.out.println(e.getMessage());
                DataBuffer dataBuffer = exchange.getResponse().bufferFactory().wrap("token不存在".getBytes());
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().writeWith(Mono.just(dataBuffer));
            }
//            email 非空判断
            if (email == null) {
                DataBuffer dataBuffer = exchange.getResponse().bufferFactory().wrap("token不对".getBytes());
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().writeWith(Mono.just(dataBuffer));
            }
                String finalEmail = email;
                String finalAid = aid;
                String finalUid = uid;
                return authorityService.getAuthority(email).flatMap(authority -> {
                    System.out.println("authority:"+authority);
                    if (uri.getPath().indexOf("admin") > 0 && !authority.equals("ADMIN")) {
                        DataBuffer dataBuffer = exchange.getResponse().bufferFactory().wrap("没权限".getBytes());
                        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                        return exchange.getResponse().writeWith(Mono.just(dataBuffer));
                    }
                    ServerHttpRequest nextRequest = request
                            .mutate()
                            .header("email", finalEmail)
                            .header("aid", finalAid)
                            .header("uid", finalUid)
                            .header("authority", authority)
                            .build();
                    ServerWebExchange nextExchange = exchange.mutate().request(nextRequest).build();
                    return chain.filter(nextExchange);
                });

        }
        else {
            System.out.println("last "+uri);
            try {
                TokenInfoDTO tokenInfoDTO = this.tokenUtils.parseBearer(auth);
                String  email = tokenInfoDTO.getEmail();
                String aid = tokenInfoDTO.getAid();
                String uid = tokenInfoDTO.getUid();

                return authorityService.getAuthority(email).flatMap(authority -> {
                    System.out.println(email);
                    System.out.println(aid);
                    System.out.println(uid);
                    ServerHttpRequest nextRequest = request
                            .mutate()
                            .header("email", email)
                            .header("aid", aid)
                            .header("uid", uid)
                            .header("authority", authority)
                            .build();
                    ServerWebExchange nextExchange = exchange.mutate().request(nextRequest).build();
                    return chain.filter(nextExchange);
                });
            } catch (Exception e) {
                ServerHttpResponse res = exchange.getResponse();
                res.setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
        }
    }

    @Override
    public int getOrder() {
        return -10;
    }
}
