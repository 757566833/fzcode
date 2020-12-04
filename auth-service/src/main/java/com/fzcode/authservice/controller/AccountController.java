package com.fzcode.authservice.controller;

import com.alibaba.fastjson.JSON;
import com.fzcode.authservice.config.Config;
import com.fzcode.authservice.dto.request.*;
import com.fzcode.authservice.dto.request.list.AccountDTO;
import com.fzcode.authservice.dto.response.GithubAccessToken;
import com.fzcode.authservice.dto.response.GithubUserInfo;
import com.fzcode.authservice.dto.response.LoginResDTO;
import com.fzcode.authservice.dto.response.SuccessResDTO;
import com.fzcode.authservice.entity.Accounts;
import com.fzcode.authservice.exception.CustomizeException;
import com.fzcode.authservice.flow.AccountFlow;
import com.fzcode.authservice.http.Oauth;
import com.fzcode.authservice.http.Websocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController()
@RequestMapping(value = "/pub")
public class AccountController {

    private AccountFlow accountFlow;

    @Autowired
    public void setAccountFlow(AccountFlow accountFlow) {
        this.accountFlow = accountFlow;
    }

    private Config config;

    @Autowired
    public void setConfig(Config config) {
        this.config = config;
    }


    @PostMapping(value = "/login")
    public SuccessResDTO login(@RequestBody @Validated LoginDTO loginDTO) throws UsernameNotFoundException {
        LoginResDTO loginResDTO = accountFlow.login(loginDTO.getEmail(), loginDTO.getPassword());
        return new SuccessResDTO("登陆成功", loginResDTO);
    }

    @PostMapping(value = "/register")
    public SuccessResDTO register(@RequestBody @Validated RegisterDTO registerDTO) throws CustomizeException {
        LoginResDTO loginResDTO = accountFlow.register(registerDTO.getEmail(), registerDTO.getPassword(), registerDTO.getCode(), registerDTO.getRegisterType());
        return new SuccessResDTO("创建成功", loginResDTO);
    }

//    @PostMapping(value = "/forget")
//    public SuccessResDTO forget(@RequestBody @Validated ForgetDTO forgetDTO) throws CustomizeException {
//        String email = accountFlow.forget(forgetDTO.getEmail(), forgetDTO.getPassword(), forgetDTO.getCode());
//        return new SuccessResDTO("修改成功", email);
//    }
//
//    @PutMapping(value = "/reset")
//    public SuccessResDTO reset(@RequestBody @Validated ResetDTO resetDTO, @RequestHeader HttpHeaders httpHeaders) throws CustomizeException {
//        String email = accountFlow.reset(httpHeaders.getFirst("email"), resetDTO.getOldPassword(), resetDTO.getNewPassword());
//        return new SuccessResDTO("修改成功", email);
//    }

    @GetMapping(value = "/github")
    public String oauth2(@RequestParam(value = "code") String code, @RequestParam(value = "socketId") String socketId) throws CustomizeException {
        GithubAccessToken githubAccessToken = Oauth.getGithubAccessToken(code);
        GithubUserInfo githubUserInfo = Oauth.getGithubUserInfo(githubAccessToken.getAccess_token());
        if (githubUserInfo.getEmail() == null) {
            return "您的github尚未绑定邮箱，具体设置方法，右上角=>settings=>profile=>public email 选择您的邮箱";
        } else {
            // 这里偷懒了 直接用长度判断 token一定长
            LoginResDTO loginResDTO = accountFlow.githubRegister(githubUserInfo);
            if (loginResDTO.getToken().length() > 15) {
                Websocket.sendUserCode(socketId, loginResDTO.getToken(), loginResDTO.getRole());
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
            return loginResDTO.getToken();
        }
    }

    @GetMapping(value = "/admin/account")
    public List<Accounts> getAccountList( @Validated AccountDTO accountDTO) {
        System.out.println(JSON.toJSONString(accountDTO));
        return accountFlow.findAllAccount(accountDTO);
    }
}
