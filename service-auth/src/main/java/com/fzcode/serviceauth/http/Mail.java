package com.fzcode.serviceauth.http;

import com.fzcode.internalcommon.constant.ServiceName;
import com.fzcode.serviceauth.dto.pri.mail.EmailReqDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class Mail {
    private LoadBalancerClient loadBalancerClient;
    private static WebClient webClient;
    @Autowired
    public void setLoadBalancerClient(LoadBalancerClient loadBalancerClient) {
        ServiceInstance instance = loadBalancerClient.choose(ServiceName.SERVICE_MAIL);
        String url ="http://" + instance.getHost() +":"+ instance.getPort() ;
        Mail.webClient = WebClient.create(url);
    }


    public static String getRegisterCode(String email) {
        EmailReqDTO emailReqDTO = new EmailReqDTO();
        emailReqDTO.setEmail(email);
        return Mail.webClient
                .post()
                .uri("/pri/email/register/code")
                .bodyValue(emailReqDTO)
                .exchange()
                .block()
                .bodyToMono(String.class)
                .block();
//        return webClient.exchange().block().bodyToMono(String.class).block();
    }
}
