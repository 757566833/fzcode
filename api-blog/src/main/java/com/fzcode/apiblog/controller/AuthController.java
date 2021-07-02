package com.fzcode.apiblog.controller;

import com.fzcode.internalcommon.constant.ServiceName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.*;
import java.util.Enumeration;

@RestController
public class AuthController {
    private LoadBalancerClient loadBalancerClient;
    @Autowired
    public void setLoadBalancerClient(LoadBalancerClient loadBalancerClient) {
        this.loadBalancerClient = loadBalancerClient;
    }

    @GetMapping(value = "/test")
    public String test (){
        String ipHostAddress = "";
        try {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress ip = (InetAddress) addresses.nextElement();
                    if (ip instanceof Inet4Address
                            && !ip.isLoopbackAddress() //loopback地址即本机地址，IPv4的loopback范围是127.0.0.0 ~ 127.255.255.255
                            && !ip.getHostAddress().contains(":")) {
                        System.out.println("本机的IP = " + ip.getHostAddress());
                        ipHostAddress = ip.getHostAddress();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "api-blog:"+ipHostAddress;

    }
    @GetMapping(value = "/test2")
    public String test2 (){
        ServiceInstance instance = this.loadBalancerClient.choose(ServiceName.SERVICE_AUTH);
        String url ="http://" + instance.getHost() +":"+ instance.getPort() ;
//        WebClient client = WebClient.create(url);
//        return client
//                .post()
//                .uri("/pri/email/register/code")
//                .bodyValue(emailReqDTO)
//                .exchange()
//                .block()
//                .bodyToMono(String.class)
//                .block();
        return url;
    }
}
