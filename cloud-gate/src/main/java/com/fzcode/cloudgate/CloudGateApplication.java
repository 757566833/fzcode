package com.fzcode.cloudgate;

import com.fzcode.cloudgate.config.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class CloudGateApplication {
    Services services;

    @Autowired
    public void setServices(Services services) {
        this.services = services;
    }

//    static AuthorityFlow authorityFlow;
//
//    @Autowired
//    public void setAuthorityFlow(AuthorityFlow authorityFlow) {
//        GateServiceApplication.authorityFlow = authorityFlow;
//    }

    public static void main(String[] args) {
        SpringApplication.run(CloudGateApplication.class, args);

    }



    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        System.out.println("--------------");
//        System.out.println(services.getFile().getBlog().getHost());
//        System.out.println("--------------");
        return builder
                .routes()
//                .route("service-auth", r -> r.path("/auth/**").filters(f -> f.stripPrefix(1)).uri(ServiceName.LB_API_BLOG))
//                .route("service-note", r -> r.path("/note/**").filters(f -> f.stripPrefix(1)).uri(services.getHost().get("note")))
//                .route("service-file", r -> r.path("/file/**").filters(f -> f.stripPrefix(1)).uri(services.getHost().get("file")))
//                .route("service-mail", r -> r.path("/mail/**").filters(f -> f.stripPrefix(1)).uri(services.getHost().get("mail")))
                .route("file-blog", r -> r.path("/file/blog/**").filters(f -> f.stripPrefix(2)).uri(services.getFile().getBlog().getHost()))
                .route("api-blog", r -> r.path("/api/blog/**").filters(f -> f.stripPrefix(2)).uri(services.getApi().getBlog().getHost()))

                // 这里面如果指向 http://www.baidu.com 之类的 他会直接转发到https上
                .route("test", r -> r.path("/test").filters(f -> f.stripPrefix(1)).uri("http://www.china.com.cn"))
                .build();
    }
    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
    }

    @Bean
    public WebClient webClient(){
        return WebClient.create();
    }
}
