package com.fzcode.serviceauth.controller;

import com.fzcode.internalcommon.dto.common.ListDTO;
import com.fzcode.internalcommon.dto.http.SuccessResponse;
import com.fzcode.internalcommon.dto.serviceauth.request.AccountListRequest;
import com.fzcode.internalcommon.dto.serviceauth.request.LoginRequest;
import com.fzcode.internalcommon.dto.serviceauth.request.RegisterRequest;
import com.fzcode.internalcommon.dto.serviceauth.response.LoginResponse;
import com.fzcode.internalcommon.dto.serviceauth.response.RegisterResponse;
import com.fzcode.internalcommon.dto.serviceauth.common.GithubAccessToken;
import com.fzcode.internalcommon.dto.serviceauth.common.GithubUserInfo;
import com.fzcode.internalcommon.dto.serviceauth.response.SelfResponse;
import com.fzcode.internalcommon.exception.CustomizeException;
import com.fzcode.serviceauth.config.Github;
import com.fzcode.serviceauth.config.Oauth;
import com.fzcode.serviceauth.entity.Accounts;
import com.fzcode.serviceauth.http.GithubAuth;
import com.fzcode.serviceauth.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

@Api(tags = "账号模块")
@RestController
public class AccountController {

    private AccountService accountService;

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    private Github github;

    @Autowired
    public void setGithub(Github github) {
        this.github = github;
    }

    private GithubAuth githubAuth;

    @Autowired
    public void setGithubAuth(GithubAuth githubAuth) {
        this.githubAuth = githubAuth;
    }

    private Oauth oauth;

    @Autowired
    public void setOauth(Oauth oauth) {
        this.oauth = oauth;
    }


    @ApiOperation(value = "登陆")
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public SuccessResponse login(@RequestBody @Validated LoginRequest loginRequest) throws CustomizeException {
        LoginResponse loginResponse = accountService.login(loginRequest.getEmail(), loginRequest.getPassword());
        return new SuccessResponse("登陆成功", loginResponse);
    }

    @ApiOperation(value = "注册")
    @PostMapping(value = "/register")
    public SuccessResponse register(@RequestBody @Validated RegisterRequest registerRequest) throws CustomizeException {
        System.out.println("register");
        RegisterResponse registerResponse = accountService.register(registerRequest.getEmail(), registerRequest.getPassword(), registerRequest.getCode(), registerRequest.getRegisterType());
        return new SuccessResponse("创建成功", registerResponse);
    }

//    @PostMapping(value = "/forget")
//    public SuccessResponse forget(@RequestBody @Validated ForgetDTO forgetDTO) throws CustomizeException {
//        String email = accountService.forget(forgetDTO.getEmail(), forgetDTO.getPassword(), forgetDTO.getCode());
//        return new SuccessResponse("修改成功", email);
//    }
//
//    @PutMapping(value = "/reset")
//    public SuccessResDTO reset(@RequestBody @Validated ResetDTO resetDTO, @RequestHeader HttpHeaders httpHeaders) throws CustomizeException {
//        String email = accountFlow.reset(httpHeaders.getFirst("email"), resetDTO.getOldPassword(), resetDTO.getNewPassword());
//        return new SuccessResDTO("修改成功", email);
//    }
    @ApiOperation(value = "获取github oauth的 code")
    @GetMapping(value = "/oauth/github/uri")
    public URI oauthGithub (@RequestParam(value = "redirect") String redirect) throws CustomizeException {
        System.out.println("获取github oauth的 code");
        URIBuilder uriBuilder;
        try {
            uriBuilder = new URIBuilder(github.getGithubAuthorize());
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new CustomizeException(HttpStatus.INTERNAL_SERVER_ERROR,"cant parse github oauth uri");
        }

        uriBuilder.addParameter("client_id",oauth.getClientId());
        uriBuilder.addParameter("scope","read:user user:email");
        URIBuilder redirectBuilder;
        try {
            redirectBuilder = new URIBuilder(github.getLoginUrl());
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new CustomizeException(HttpStatus.INTERNAL_SERVER_ERROR,"cant parse server login uri");
        }
        redirectBuilder.addParameter("redirect",redirect);
        URI redirectURI;
        try {
             redirectURI =  redirectBuilder.build();
        }catch (URISyntaxException e){
            e.printStackTrace();
            throw new CustomizeException(HttpStatus.INTERNAL_SERVER_ERROR,"cant build server login uri ");
        }

        uriBuilder.addParameter("redirect_uri",redirectURI.toString());
        URI uri;
        try {
            uri = uriBuilder.build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw  new CustomizeException(HttpStatus.INTERNAL_SERVER_ERROR,"cant build github oauth uri");
        }

        return uri;
//        System.out.println(uri);
//        headers.setLocation(uri);
//        ResponseEntity responseEntity = new ResponseEntity(headers, HttpStatus.MOVED_PERMANENTLY);
//        httpServletResponse.sendRedirect("https://github.com/login/oauth/authorize?client_id="+oauth.getClientId()+"&scope=read:user user:email&redirect_uri="+github.getLoginUrl());

//return "https://github.com/login/oauth/authorize?client_id="+oauth.getClientId()+"&scope=read:user user:email&redirect_uri="+github.getLoginUrl();
//        URI uri = URI.create("https://github.com/login/oauth/authorize?client_id="+oauth.getClientId()+"&scope=read:user user:email&redirect_uri="+github.getLoginUrl());
    }

    @ApiOperation(value = "github登陆")
    @GetMapping(value = "/login/github")
    public String oauth2(@RequestParam(value = "code") String code,@RequestParam(value = "redirect") String redirect) throws CustomizeException {
        System.out.println("auth:"+code);
        GithubAccessToken githubAccessToken = githubAuth.getGithubAccessToken(code);
        GithubUserInfo githubUserInfo = githubAuth.getGithubUserInfo(githubAccessToken.getAccess_token());
        if (githubUserInfo.getEmail() == null) {
            throw new CustomizeException(HttpStatus.INTERNAL_SERVER_ERROR,"您的github尚未绑定邮箱，具体设置方法，右上角=>settings=>profile=>public email 选择您的邮箱");
        } else {
            URIBuilder uriBuilder;
            try {
                uriBuilder = new URIBuilder(github.getSuccessUrl());
            } catch (URISyntaxException e) {
                e.printStackTrace();
                System.out.println("解析uri路径出错");
                throw new CustomizeException(HttpStatus.INTERNAL_SERVER_ERROR,"解析uri路径出错");
            }
            RegisterResponse registerResponse = accountService.githubRegister(githubUserInfo);
            uriBuilder.addParameter("redirect",redirect);
            uriBuilder.addParameter("token",registerResponse.getToken());
            uriBuilder.addParameter("role",registerResponse.getRole());
            URI uri;
            try {
                uri = uriBuilder.build();
            } catch (URISyntaxException e) {
                e.printStackTrace();
                System.out.println("解析uri参数出错");
                throw  new CustomizeException(HttpStatus.INTERNAL_SERVER_ERROR,"解析uri参数出错");
            }
            return uri.toString();

        }
    }

    @ApiOperation(value = "获取个人信息")
    @GetMapping("/self")
    public SelfResponse getSelf(@RequestHeader("aid") String aid) throws CustomizeException {
        return accountService.findFirstByAid(aid);
    }

    @ApiOperation(value = "获取账号列表（必须是管理员）")
    @GetMapping(value = "/admin/account")
    public ListDTO<Accounts> getAccountList(@Validated AccountListRequest accountListRequest) {
        return accountService.findAllAccount(accountListRequest);
    }


}
