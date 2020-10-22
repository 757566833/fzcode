package com.fzcode.authservice.controller;

import com.fzcode.authservice.bean.MyUser;
import com.fzcode.authservice.dto.common.TokenDTO;
import com.fzcode.authservice.dto.request.LoginDTO;
import com.fzcode.authservice.dto.request.RegisterDTO;
import com.fzcode.authservice.dto.request.ForgetDTO;
import com.fzcode.authservice.dto.request.ResetDTO;
import com.fzcode.authservice.dto.response.SuccessResDTO;
import com.fzcode.authservice.exception.CustomizeException;
import com.fzcode.authservice.flow.AuthFlow;
import com.fzcode.authservice.service.UserService;
import com.fzcode.authservice.util.JwtUtils;
import com.fzcode.authservice.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;

@RestController()
@RequestMapping(value = "/auth")
public class AuthController {

    private AuthFlow authFlow;

    @Autowired
    public void setAuthFlow(AuthFlow authFlow) {
        this.authFlow = authFlow;
    }

    @Value("${auth.secret:N/A}")
    private String greeting;


    @GetMapping(value = "/test")
    public String test() {
        return greeting;
    }

    @PostMapping(value = "/login")
    public SuccessResDTO login(@RequestBody @Validated LoginDTO loginDTO) throws UsernameNotFoundException {
        return authFlow.login(loginDTO.getEmail(), loginDTO.getPassword());
    }

    @PostMapping(value = "/register")
    public SuccessResDTO register(@RequestBody @Validated RegisterDTO registerDTO) throws CustomizeException {
        return authFlow.register(registerDTO.getEmail(), registerDTO.getPassword(), registerDTO.getCode(), registerDTO.getRegisterType());
    }

    @PostMapping(value = "/forget")
    public SuccessResDTO forget(@RequestBody @Validated ForgetDTO forgetDTO) throws CustomizeException {
        return authFlow.forget(forgetDTO.getEmail(), forgetDTO.getPassword(), forgetDTO.getCode());
    }

    @PutMapping(value = "/reset")
    public SuccessResDTO reset(@RequestBody @Validated ResetDTO resetDTO, @RequestHeader HttpHeaders httpHeaders) throws CustomizeException {
        String email = httpHeaders.getFirst("email");
        return authFlow.reset(email, resetDTO.getOldPassword(), resetDTO.getNewPassword());
    }

}
