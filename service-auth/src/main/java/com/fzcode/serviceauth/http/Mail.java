package com.fzcode.serviceauth.http;

import com.fzcode.internalcommon.constant.ServiceNameConstant;
import com.fzcode.internalcommon.dto.servicemail.common.MailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class Mail {
    private LoadBalancerClient loadBalancerClient;
    @Autowired
    public void setLoadBalancerClient(LoadBalancerClient loadBalancerClient) {
     this.loadBalancerClient = loadBalancerClient;
    }

    public String getRegisterCode(String email) {
        ServiceInstance instance = this.loadBalancerClient.choose(ServiceNameConstant.SERVICE_MAIL);
        String url ="http://" + instance.getHost() +":"+ instance.getPort() ;
        WebClient client = WebClient.create(url);
        MailDTO emailReqDTO = new MailDTO();
        emailReqDTO.setEmail(email);
        return client
                .post()
                .uri("/pri/email/register/code")
                .bodyValue(emailReqDTO)
                .exchange()
                .block()
                .bodyToMono(String.class)
                .block();
    }
}
