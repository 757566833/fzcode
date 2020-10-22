package com.fzcode.gateservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GateServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GateServiceApplication.class, args);
	}
	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder){
		return builder
				.routes()
				.route("resource-service",r->r.path("/resource/**").uri("http://127.0.0.1:9001"))
				.route("auth-service",r->r.path("/auth/**").uri("http://127.0.0.1:9011"))
				.route("examination-service",r->r.path("/examination/**").uri("http://127.0.0.1:9021"))
				.route("excel-service",r->r.path("/excel/**").uri("http://127.0.0.1:9031"))
				.route("file-service",r->r.path("/file/**").uri("http://127.0.0.1:9041"))
				.route("mail-service",r->r.path("/mail/**").uri("http://127.0.0.1:9051"))


				// 这里面如果指向 http://www.baidu.com 之类的 他会直接转发到https上
				.route("test",r->r.path("/test").filters(f->f.stripPrefix(1)).uri("http://www.china.com.cn"))
//				.route("test2",r->r.path("/test2").filters(f->f.stripPrefix(1)).uri("lb://account-service"))
//
				.build();
	}
}
