package com.fzcode.gateservice;

import com.fzcode.gateservice.config.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GateServiceApplication {
    Services services;

    @Autowired
    public void setConfig(Services services) {
        this.services = services;
    }

//    static AuthorityFlow authorityFlow;
//
//    @Autowired
//    public void setAuthorityFlow(AuthorityFlow authorityFlow) {
//        GateServiceApplication.authorityFlow = authorityFlow;
//    }

    public static void main(String[] args) {
        SpringApplication.run(GateServiceApplication.class, args);

    }



    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        System.out.println(services.getHost().get("auth"));
        return builder
                .routes()
                .route("auth-service", r -> r.path("/auth/**").filters(f -> f.stripPrefix(1)).uri(services.getHost().get("auth")))
                .route("note-service", r -> r.path("/note/**").filters(f -> f.stripPrefix(1)).uri(services.getHost().get("note")))
                .route("file-service", r -> r.path("/file/**").filters(f -> f.stripPrefix(1)).uri(services.getHost().get("file")))
                .route("mail-service", r -> r.path("/mail/**").filters(f -> f.stripPrefix(1)).uri(services.getHost().get("mail")))


                // 这里面如果指向 http://www.baidu.com 之类的 他会直接转发到https上
                .route("test", r -> r.path("/test").filters(f -> f.stripPrefix(1)).uri("http://www.china.com.cn"))
//				.route("test2",r->r.path("/test2").filters(f->f.stripPrefix(1)).uri("lb://account-service"))
//
                .build();
    }
}
