package com.fzcode.apiblog.controller;

import com.alibaba.fastjson.JSON;
import com.fzcode.apiblog.config.Services;
import com.fzcode.internalcommon.dto.http.SuccessResponse;
import com.fzcode.internalcommon.dto.serviceauth.request.LoginRequest;
import com.fzcode.internalcommon.dto.serviceauth.response.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {
    Services services;
    @Autowired
    public void setServices(Services services){
        this.services = services;
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
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public LoginResponse login (@RequestBody @Validated LoginRequest loginRequest){
        WebClient client = WebClient.create(services.getService().getAuth().getHost());
        return client
                .post()
                .uri("/login")
                .bodyValue(loginRequest)
                .exchange()
                .block()
                .bodyToMono(LoginResponse.class)
                .block();
    }
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public LoginResponse register (@RequestBody @Validated LoginRequest loginRequest){
        WebClient client = WebClient.create(services.getService().getAuth().getHost());
        return client
                .post()
                .uri("/register")
                .bodyValue(loginRequest)
                .exchange()
                .block()
                .bodyToMono(LoginResponse.class)
                .block();
    }
    @GetMapping(value = "/login/github")
    public String githubLogin (@RequestParam(value = "code") String code, @RequestParam(value = "socketId") String socketId){
        Map map = new HashMap();
        map.put("code",code);
        map.put("socketId",socketId);
        System.out.println(JSON.toJSONString(map));
        WebClient client = WebClient.create(services.getService().getAuth().getHost());
        return client
                .post()
                .uri("/login/github")
                .bodyValue(map)
                .exchange()
                .block()
                .bodyToMono(String.class)
                .block();
    }

//      .header("email", finalEmail)
//                            .header("aid", finalAid.toString())
//            .header("userAuthority", authority)
    @GetMapping(value = "/self")
    public SuccessResponse getSelf (@RequestHeader("email") String email,@RequestHeader("aid") String aid,@RequestHeader("authority") String authority){
        WebClient client = WebClient.create(services.getService().getAuth().getHost());
        Map<String, Object> info =  client
                .get()
                .uri("/self")
                .header("email",email)
                .header("aid",aid)
                .header("authority",authority)
                .exchange()
                .block()
                .bodyToMono(Map.class)
                .block();
        return  new SuccessResponse("查询成功",info);
    }

}
