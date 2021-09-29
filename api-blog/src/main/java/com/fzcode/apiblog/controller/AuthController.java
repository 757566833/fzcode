package com.fzcode.apiblog.controller;

import com.alibaba.fastjson.JSON;
import com.fzcode.apiblog.config.Services;
import com.fzcode.apiblog.exception.CustomizeException;
import com.fzcode.internalcommon.dto.common.ListResponseDTO;
import com.fzcode.internalcommon.dto.http.SuccessResponse;
import com.fzcode.internalcommon.dto.serviceauth.request.AccountListRequest;
import com.fzcode.internalcommon.dto.serviceauth.request.LoginRequest;
import com.fzcode.internalcommon.dto.serviceauth.request.RegisterRequest;
import com.fzcode.internalcommon.dto.serviceauth.response.LoginResponse;
import com.fzcode.internalcommon.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
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
    WebClient client;
    Services services;
    @Autowired
    public void setServices(Services services){
        this.services = services;
        client = WebClient.create(services.getService().getAuth().getHost());
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
    public String register (@RequestBody @Validated RegisterRequest registerRequest){
        System.out.println("register");
        System.out.println(services.getService().getAuth().getHost());
        return client
                .post()
                .uri("/register")
                .bodyValue(registerRequest)
                .exchange()
                .block()
                .bodyToMono(String.class)
                .block();
    }
    @GetMapping(value = "/login/github")
    public String githubLogin (@RequestParam(value = "code") String code, @RequestParam(value = "socketId") String socketId){
        Map map = new HashMap();
        map.put("code",code);
        map.put("socketId",socketId);
        System.out.println(JSON.toJSONString(map));
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
//            .header("authority", authority)
    @GetMapping(value = "/self")
    public SuccessResponse getSelf (@RequestHeader("email") String email,@RequestHeader("aid") String aid,@RequestHeader("authority") String authority){
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
    @GetMapping(value = "/admin/account")
    public SuccessResponse getUserList (AccountListRequest accountListRequest , @RequestHeader("email") String email, @RequestHeader("aid") String aid) throws CustomizeException {
        System.out.println("email:"+email+",aid:"+aid);
        MultiValueMap<String, String> params = BeanUtils.bean2MultiValueMap(accountListRequest);
        ListResponseDTO<Map<String, Object>> info =  client
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/admin/account")
                        .queryParams(params)
                        .build())
                .header("email",email)
                .header("aid",aid)
                .exchange()
                .block()
                .bodyToMono(ListResponseDTO.class)
                .block();
        return  new SuccessResponse("查询成功",info);
    }
}
