package com.fzcode.apiblog.controller;

import com.fzcode.apiblog.config.Services;
import com.fzcode.internalcommon.dto.common.ListResponseDTO;
import com.fzcode.internalcommon.dto.http.SuccessResponse;
import com.fzcode.internalcommon.dto.serviceauth.request.AccountListRequest;
import com.fzcode.internalcommon.dto.serviceauth.request.LoginRequest;
import com.fzcode.internalcommon.dto.serviceauth.request.RegisterRequest;
import com.fzcode.internalcommon.dto.serviceauth.response.LoginResponse;
import com.fzcode.internalcommon.exception.CustomizeException;
import com.fzcode.internalcommon.utils.ObjectUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.*;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "账权模块")
@RestController
@RequestMapping(value = "/auth")
public class AuthController {
    RestTemplate restTemplate;
    Services services;
    @Autowired
    public void setRestTemplate(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }
    @Autowired
    public void setServices(Services services){
        this.services = services;
    }
    @ApiOperation(value = "测试接口")
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

    @ApiOperation(value = "账号密码登陆")
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public LoginResponse login (@RequestBody @Validated LoginRequest loginRequest){
        return  restTemplate.postForObject(services.getService().getAuth().getHost()+"/login",loginRequest,LoginResponse.class);
    }

    @ApiOperation(value = "账号密码注册")
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String register (@RequestBody @Validated RegisterRequest registerRequest){
        return  restTemplate.postForObject(services.getService().getAuth().getHost()+"/register",registerRequest,String.class);
    }

    @ApiOperation(value = "github方式登陆")
    @GetMapping(value = "/login/github")
    public String githubLogin (@RequestParam(value = "code") String code, @RequestParam(value = "socketId") String socketId){
        Map map = new HashMap();
        map.put("code",code);
        map.put("socketId",socketId);
        return  restTemplate.postForObject(services.getService().getAuth().getHost()+"/login/github",map,String.class);
    }

//      .header("email", finalEmail)
//                            .header("aid", finalAid.toString())
//            .header("authority", authority)

    @ApiOperation(value = "获取当前用户信息")
    @GetMapping(value = "/self")
    public SuccessResponse getSelf (@RequestHeader("email") String email,@RequestHeader("aid") String aid,@RequestHeader("authority") String authority){
        LinkedMultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.put("email", Collections.singletonList(aid));
        headers.put("aid", Collections.singletonList(aid));
        headers.put("authority", Collections.singletonList(authority));
        HttpEntity<String> request =  new HttpEntity<String>(null, headers);
        ResponseEntity<Map> resEntity =  restTemplate.exchange(services.getService().getAuth().getHost()+"/self", HttpMethod.GET, request, Map.class);
        return  new SuccessResponse("查询成功",resEntity.toString());
    }

    @ApiOperation(value = "管理员获取所有用户列表")
    @GetMapping(value = "/admin/account")
    public SuccessResponse getUserList (AccountListRequest accountListRequest , @RequestHeader("email") String email, @RequestHeader("aid") String aid) throws CustomizeException {
        System.out.println("email:"+email+",aid:"+aid);
        MultiValueMap<String, String> params = ObjectUtils.object2MultiValueMap(accountListRequest);
        LinkedMultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.put("email", Collections.singletonList(aid));
        headers.put("aid", Collections.singletonList(aid));
        HttpEntity<String> request =  new HttpEntity<String>(null, headers);
        ResponseEntity<ListResponseDTO> resEntity =  restTemplate.exchange(services.getService().getAuth().getHost()+"/admin/account", HttpMethod.GET, request, ListResponseDTO.class);
        return  new SuccessResponse("查询成功",resEntity);
    }
}
