package com.fzcode.serviceauth.http;

import com.fzcode.internalcommon.constant.ServiceName;
import com.fzcode.serviceauth.dto.pri.mail.EmailReqDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.PostConstruct;

@Component
public class Mail {
    private LoadBalancerClient loadBalancerClient;
    @Autowired
    public void setLoadBalancerClient(LoadBalancerClient loadBalancerClient) {
     this.loadBalancerClient = loadBalancerClient;
    }

    public String getRegisterCode(String email) {
        ServiceInstance instance = this.loadBalancerClient.choose(ServiceName.SERVICE_MAIL);
        String host = instance.getHost();
        Integer port = instance.getPort();
        System.out.println(host);
        System.out.println(port);
        String url ="http://" + instance.getHost() +":"+ instance.getPort() ;
        WebClient client = WebClient.create(url);
        EmailReqDTO emailReqDTO = new EmailReqDTO();
        emailReqDTO.setEmail(email);
        return client
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
