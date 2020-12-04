package com.fzcode.gateservice;

import com.fzcode.gateservice.config.Config;
import com.fzcode.gateservice.flow.AuthorityFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GateServiceApplication {
    Config config;

    @Autowired
    public void setConfig(Config config) {
        this.config = config;
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
        return builder
                .routes()
                .route("auth-service", r -> r.path("/auth/pub/**").filters(f -> f.stripPrefix(1)).uri(config.getUrl() + ":9111"))
                .route("note-service", r -> r.path("/note/pub/**").filters(f -> f.stripPrefix(1)).uri(config.getUrl() + ":9121"))
                .route("file-service", r -> r.path("/file/pub/**").filters(f -> f.stripPrefix(1)).uri(config.getUrl() + ":9141"))
                .route("mail-service", r -> r.path("/mail/pub/**").filters(f -> f.stripPrefix(1)).uri(config.getUrl() + ":9151"))


                // 这里面如果指向 http://www.baidu.com 之类的 他会直接转发到https上
                .route("test", r -> r.path("/test").filters(f -> f.stripPrefix(1)).uri("http://www.china.com.cn"))
//				.route("test2",r->r.path("/test2").filters(f->f.stripPrefix(1)).uri("lb://account-service"))
//
                .build();
    }
}
