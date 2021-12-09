package com.fzcode.serviceauth.controller;

import com.fzcode.internalcommon.dto.common.ListResponseDTO;
import com.fzcode.internalcommon.dto.http.SuccessResponse;
import com.fzcode.internalcommon.dto.serviceauth.request.AccountListRequest;
import com.fzcode.internalcommon.dto.serviceauth.request.GithubLoginRequest;
import com.fzcode.internalcommon.dto.serviceauth.request.LoginRequest;
import com.fzcode.internalcommon.dto.serviceauth.request.RegisterRequest;
import com.fzcode.internalcommon.dto.serviceauth.response.LoginResponse;
import com.fzcode.internalcommon.dto.serviceauth.response.RegisterResponse;
import com.fzcode.internalcommon.dto.serviceauth.common.GithubAccessToken;
import com.fzcode.internalcommon.dto.serviceauth.common.GithubUserInfo;
import com.fzcode.internalcommon.exception.CustomizeException;
import com.fzcode.internalcommon.utils.JSONUtils;
import com.fzcode.serviceauth.config.Github;
import com.fzcode.serviceauth.config.Oauth;
import com.fzcode.serviceauth.service.AccountService;
import com.fzcode.serviceauth.http.Auth;
import com.fzcode.serviceauth.util.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping(value = "/oauth/github")
    @ResponseStatus(HttpStatus.MOVED_PERMANENTLY)
    public String oauthGithub (){
        return "https://github.com/login/oauth/authorize?client_id="+oauth.getClientId()+"&scope=read:user user:email&redirect_uri="+github.getLoginUrl();
    }

    @ApiOperation(value = "github登陆")
    @PostMapping(value = "/login/github")
    public String oauth2(@RequestBody @Validated GithubLoginRequest githubLoginRequest) throws CustomizeException {


        GithubAccessToken githubAccessToken = Auth.getGithubAccessToken(githubLoginRequest.getCode());
        GithubUserInfo githubUserInfo = Auth.getGithubUserInfo(githubAccessToken.getAccess_token());
        if (githubUserInfo.getEmail() == null) {
            return "您的github尚未绑定邮箱，具体设置方法，右上角=>settings=>profile=>public email 选择您的邮箱";
        } else {
            // 这里偷懒了 直接用长度判断 token一定长
            RegisterResponse registerResponse = accountService.githubRegister(githubUserInfo);
            if (registerResponse.getToken().length() > 15) {
                return "http://127.0.0.1:3000/github/login/success?token"+registerResponse.getToken()+"&role="+registerResponse.getRole();
            }else{
                return  registerResponse.getToken();
            }

        }
    }

    @ApiOperation(value = "获取个人信息")
    @GetMapping("/self")
    public Map<String, Object> getSelf(@RequestHeader("aid") String aid) throws CustomizeException {

        return accountService.findByAid(Integer.parseInt(aid));
    }

    @ApiOperation(value = "获取账号列表（必须是管理员）")
    @GetMapping(value = "/admin/account")
    public ListResponseDTO<Map<String, Object>> getAccountList(@Validated AccountListRequest accountListRequest) {
        return accountService.findAllAccount(accountListRequest);
    }


}
