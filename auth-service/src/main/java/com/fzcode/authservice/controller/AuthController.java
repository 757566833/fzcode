package com.fzcode.authservice.controller;

import com.fzcode.authservice.bean.MyUser;
import com.fzcode.authservice.dto.common.TokenDTO;
import com.fzcode.authservice.dto.request.LoginDTO;
import com.fzcode.authservice.dto.request.RegisterDTO;
import com.fzcode.authservice.dto.request.ForgetDTO;
import com.fzcode.authservice.dto.request.ResetDTO;
import com.fzcode.authservice.dto.response.SuccessResDTO;
import com.fzcode.authservice.exception.CustomizeException;
import com.fzcode.authservice.service.UserService;
import com.fzcode.authservice.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    DataSource dataSource;


    @GetMapping(value = "/test")
    public String test() {
        return "test";
    }

    @PostMapping(value = "/test2")
    public String test2() {
        return "test2";
    }

    @PostMapping(value = "/login")
    public SuccessResDTO login(@RequestBody @Validated LoginDTO loginDTO) throws UsernameNotFoundException {
        UserService userService = new UserService(dataSource);
        UserDetails userDetails = userService.loadUserByUsername(loginDTO.getEmail());
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (!bCryptPasswordEncoder.matches(loginDTO.getPassword(), userDetails.getPassword())) {
            throw new UsernameNotFoundException("用户名密码错误");
        }
        return new SuccessResDTO("登陆成功", new TokenDTO(JwtUtils.createToken(loginDTO.getEmail())));

    }

    @PostMapping(value = "/register")
    public SuccessResDTO register(@RequestBody @Validated RegisterDTO registerDTO) throws CustomizeException {
        System.out.println(registerDTO);
        UserService userService = new UserService(dataSource);
        boolean b = userService.userExists(registerDTO.getEmail());
        if (b) {
            throw new CustomizeException("邮箱已存在");
        }
        try {
            userService.createUser(
                    MyUser
                            .withMyUsername(registerDTO.getEmail())
                            .password(new BCryptPasswordEncoder().encode(registerDTO.getPassword()))
                            .accountExpired(true)
                            .accountLocked(true)
                            .registerType(registerDTO.getRegisterType())
                            .roles("USER")
                            .build()
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new CustomizeException("账号创建失败");
        }
        return new SuccessResDTO("创建成功", new TokenDTO(JwtUtils.createToken(registerDTO.getEmail())));

    }

    @PostMapping(value = "/forget")
    public SuccessResDTO forget(@RequestBody @Validated ForgetDTO forgetDTO) throws CustomizeException {
        UserService userService = new UserService(dataSource);
        boolean b = userService.userExists(forgetDTO.getEmail());
        if (!b) {
            throw new CustomizeException("账号不存在");
        }
//        if 验证码正确
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        try {
            userService.resetPassword(forgetDTO.getEmail(), bCryptPasswordEncoder.encode(forgetDTO.getPassword()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new CustomizeException("修改失败");
        }
        return new SuccessResDTO("修改成功");

    }

    @PutMapping(value = "/reset")
    public SuccessResDTO reset(@RequestBody @Validated ResetDTO resetDTO, @RequestHeader HttpHeaders httpHeaders) throws CustomizeException {

        UserService userService = new UserService(dataSource);
        String email = httpHeaders.getFirst("email");
        boolean b = userService.userExists(email);
        if (!b) {
            throw new CustomizeException("账号不存在");
        }
        UserDetails userDetails = userService.loadUserByUsername(email);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (!bCryptPasswordEncoder.matches(resetDTO.getOldPassword(), userDetails.getPassword())) {
            throw new UsernameNotFoundException("用户名密码错误");
        }
        try {
            userService.changePassword(email, resetDTO.getNewPassword(), resetDTO.getNewPassword());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new CustomizeException("修改失败");
        }
        return new SuccessResDTO("修改成功");

    }
}
