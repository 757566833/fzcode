package com.fzcode.serviceauth.http;

import org.springframework.beans.factory.annotation.Autowired;
import com.fzcode.internalcommon.constant.ServiceNameConstant;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@Component
public class Gate {
    private LoadBalancerClient loadBalancerClient;
    private static WebClient webClient;
    @Autowired
    public void setLoadBalancerClient(LoadBalancerClient loadBalancerClient) {
        ServiceInstance instance = loadBalancerClient.choose(ServiceNameConstant.CLOUD_GATE);
        String url ="http://" + instance.getHost() +":"+ instance.getPort() ;
        Gate.webClient = WebClient.create(url);
    }

    public static void updateAuthority(String account, String authority) {
//        EmailReqDTO emailReqDTO = new EmailReqDTO();
//        emailReqDTO.setEmail(email);
        Map<String, String> map = new HashMap<>();
        map.put("account", account);
        map.put("authority", authority);
        Gate.webClient
                .post()
                .uri("/pri/refresh/authority")
                .bodyValue(map)
                .exchange()
                .block()
                .bodyToMono(String.class)
                .block();
//        return webClient.exchange().block().bodyToMono(String.class).block();
    }
}
