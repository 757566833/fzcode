//package com.examination.gateservice.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.server.ServerHttpRequest;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//
//@Configuration
//@EnableWebFluxSecurity
//public class SecurityConfig  {
//
//    @Bean
//    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity serverHttpSecurity){
//        return serverHttpSecurity
//                .csrf().disable()
//                .cors()
//                .and()
//                .authorizeExchange(e->
//                        e
//                                .pathMatchers(HttpMethod.OPTIONS).permitAll()
//                                .pathMatchers("/auth/**","/mail/**").permitAll()
//
//                                .anyExchange().authenticated()
//                )
////                .httpBasic()
////                .and()
//                .formLogin()
//                .and()
//                .build();
//    }
//}
