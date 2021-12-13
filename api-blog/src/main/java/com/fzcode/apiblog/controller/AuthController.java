package com.fzcode.apiblog.controller;

import com.fzcode.apiblog.config.Services;
import com.fzcode.internalcommon.dto.common.ListResponseDTO;
import com.fzcode.internalcommon.dto.http.SuccessResponse;
import com.fzcode.internalcommon.dto.serviceauth.request.*;
import com.fzcode.internalcommon.exception.CustomizeException;
import com.fzcode.internalcommon.http.Http;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URI;
import java.util.Enumeration;
import java.util.Map;

@Api(tags = "账权模块")
@RestController
@RequestMapping(value = "/auth")
public class AuthController {
    Http http;
    Services services;
    @Autowired
    public void setHttp(Http http){
        this.http = http;
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
    public String login (@RequestBody @Validated LoginRequest loginRequest) throws CustomizeException {
        return  http.post(services.getService().getAuth().getHost()+"/login",loginRequest,String.class);
    }

    @ApiOperation(value = "账号密码注册")
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String register (@RequestBody @Validated RegisterRequest registerRequest) throws CustomizeException {
        return  http.post(services.getService().getAuth().getHost()+"/register",registerRequest,String.class);
    }
    @ApiOperation(value = "跳转github授权")
    @GetMapping(value = "/oauth/github")
    public ResponseEntity oauthGithub (@RequestParam(value = "redirect") String redirect) throws CustomizeException {
        System.out.println("github获取");
        OauthGithubUriRequest oauthGithubUriRequest = new OauthGithubUriRequest();
        oauthGithubUriRequest.setRedirect(redirect);
        URI uri = http.get(services.getService().getAuth().getHost()+"/oauth/github/uri",oauthGithubUriRequest, URI.class);
        HttpHeaders headers = new HttpHeaders();
        System.out.println(uri);
        headers.setLocation(uri);
        ResponseEntity responseEntity = new ResponseEntity(headers, HttpStatus.MOVED_PERMANENTLY);
        return responseEntity;
//        httpServletResponse.sendRedirect(str);
//        return  http.get(services.getService().getAuth().getHost()+"/oauth/github",String.class);
    }

    @ApiOperation(value = "github方式登陆")
    @GetMapping(value = "/login/github")
    public ResponseEntity githubLogin (@RequestParam(value = "code") String code,@RequestParam(value = "redirect") String redirect) throws CustomizeException {
        LoginGithub loginGithub = new  LoginGithub();
        loginGithub.setCode(code);
        loginGithub.setRedirect(redirect);
        String str =  http.get(services.getService().getAuth().getHost()+"/login/github",loginGithub,String.class);
        System.out.println("after http");
        if(str.startsWith("http")){
            HttpHeaders headers = new HttpHeaders();
            System.out.println(str);
            headers.setLocation(URI.create(str));
            ResponseEntity responseEntity = new ResponseEntity(headers, HttpStatus.MOVED_PERMANENTLY);
            return responseEntity;
        }else {
            ResponseEntity responseEntity = new ResponseEntity(str, HttpStatus.OK);
            return responseEntity;
        }
//        return  http.post(services.getService().getAuth().getHost()+"/login/github",map,String.class);
    }

    @ApiOperation(value = "获取当前用户信息")
    @GetMapping(value = "/self")
    public SuccessResponse getSelf (@RequestHeader("email") String email, @RequestHeader("aid") String aid, @RequestHeader("authority") String authority) throws CustomizeException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("email",email);
        headers.add("aid",aid);
        headers.add("authority",authority);
        Map map =  http.get(services.getService().getAuth().getHost()+"/self", headers, Map.class);
        return  new SuccessResponse("查询成功",map);
    }

    @ApiOperation(value = "管理员获取所有用户列表")
    @GetMapping(value = "/admin/account")
    public SuccessResponse getUserList (AccountListRequest accountListRequest , @RequestHeader("email") String email, @RequestHeader("aid") String aid) throws CustomizeException {
//        MultiValueMap<String, String> params = ObjectUtils.object2MultiValueMap(accountListRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.add("email",email);
        headers.add("aid",aid);
        ListResponseDTO resEntity =  http.get(services.getService().getAuth().getHost()+"/admin/account", accountListRequest,headers, ListResponseDTO.class);
        return  new SuccessResponse("查询成功",resEntity);
    }
}
