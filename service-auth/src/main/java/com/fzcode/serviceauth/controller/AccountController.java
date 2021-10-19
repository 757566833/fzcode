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
import com.fzcode.internalcommon.utils.JSONUtils;
import com.fzcode.serviceauth.exception.CustomizeException;
import com.fzcode.serviceauth.service.AccountService;
import com.fzcode.serviceauth.http.Auth;
import com.fzcode.serviceauth.util.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Map;

@Api(tags = "账号模块")
@RestController
public class AccountController {

    private AccountService accountService;

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
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
                MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
                map.add("socketId", githubLoginRequest.getSocketId());
                map.add("token", registerResponse.getToken());
                map.add("role", registerResponse.getRole());
//                RedisUtils.publishing(JSON.toJSONString(map));
                RedisUtils.publishing(JSONUtils.stringify(map));
                return "<!DOCTYPE html>\n" +
                        "<html lang=\"en\">\n" +
                        "<head>\n" +
                        "  <meta charset=\"UTF-8\">\n" +
                        "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                        "  <title>Document</title>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "  <div>登陆成功,<span id ='second'>5</span>秒后关闭页面，原页面未刷新请手动刷新</div>\n" +
                        "</body>\n" +
                        "<script>\n" +
                        "  var secondDom =  document.getElementById(\"second\");\n" +
                        "  var second = 5\n" +
                        "  setInterval(()=>{\n" +
                        "    secondDom.innerText=--second;\n" +
                        "    if(second==0){\n" +
                        "      window.close();\n" +
                        "    }\n" +
                        "  },1000);\n" +
                        "</script>\n" +
                        "</html>\n";
            }
            return registerResponse.getToken();
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
