package com.fzcode.authservice.flow;

import com.fzcode.authservice.bean.MyUser;
import com.fzcode.authservice.dto.common.TokenDTO;
import com.fzcode.authservice.dto.request.ForgetDTO;
import com.fzcode.authservice.dto.request.LoginDTO;
import com.fzcode.authservice.dto.request.RegisterDTO;
import com.fzcode.authservice.dto.request.ResetDTO;
import com.fzcode.authservice.dto.response.SuccessResDTO;
import com.fzcode.authservice.exception.CustomizeException;
import com.fzcode.authservice.service.UserService;
import com.fzcode.authservice.util.JwtUtils;
import com.fzcode.authservice.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.sql.DataSource;

@Component
public class AuthFlow {

    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public String login(String email, String password) throws UsernameNotFoundException {
        UserService userService = new UserService(dataSource);
        UserDetails userDetails = userService.loadUserByUsername(email);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (!bCryptPasswordEncoder.matches(password, userDetails.getPassword())) {
            throw new UsernameNotFoundException("用户名密码错误");
        }
        return JwtUtils.createToken(email);
    }

    public String register(String email, String password, String code, String registerType) throws CustomizeException {
        UserService userService = new UserService(dataSource);
        boolean b = userService.userExists(email);
        if (b) {
            throw new CustomizeException("邮箱已存在");
        }
        if (!RedisUtils.getString(email + ":" + "register").equals(code)) {
            throw new CustomizeException("验证码错误");
        }
        try {
            userService.createUser(
                    MyUser
                            .withMyUsername(email)
                            .password(new BCryptPasswordEncoder().encode(password))
                            .accountExpired(true)
                            .accountLocked(true)
                            .registerType(registerType)
                            .roles("USER")
                            .build()
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new CustomizeException("账号创建失败");
        }
        return JwtUtils.createToken(email);
//        return new SuccessResDTO("创建成功", new TokenDTO(JwtUtils.createToken(email)));

    }

    public String forget(String email, String password, String code) throws CustomizeException {
        UserService userService = new UserService(dataSource);
        boolean b = userService.userExists(email);
        if (!b) {
            throw new CustomizeException("账号不存在");
        }
        if (!RedisUtils.getString(email + ":" + "forget").equals(code)) {
            throw new CustomizeException("验证码错误");
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        try {
            userService.resetPassword(email, bCryptPasswordEncoder.encode(password));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new CustomizeException("修改失败");
        }
        return email;

    }

    public String reset(String email, String oldPassword, String newPassword) throws CustomizeException {
        UserService userService = new UserService(dataSource);
        boolean b = userService.userExists(email);
        if (!b) {
            throw new CustomizeException("账号不存在");
        }
        UserDetails userDetails = userService.loadUserByUsername(email);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (!bCryptPasswordEncoder.matches(oldPassword, userDetails.getPassword())) {
            throw new UsernameNotFoundException("用户名密码错误");
        }
        try {
            userService.changePassword(email, newPassword, newPassword);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new CustomizeException("修改失败");
        }
        return email;

    }

    public String githubRegister(String email, String password, String registerType) throws CustomizeException {
        UserService userService = new UserService(dataSource);
        boolean b = userService.userExists(email);
        if (b) {
            UserDetails userDetails = userService.loadUserByUsername(email);
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            if (!bCryptPasswordEncoder.matches(password, userDetails.getPassword())) {
                return "账号或密码错误，请找回密码";
            }
        } else {
            try {
                userService.createUser(
                        MyUser
                                .withMyUsername(email)
                                .password(new BCryptPasswordEncoder().encode(password))
                                .accountExpired(true)
                                .accountLocked(true)
                                .registerType(registerType)
                                .roles("USER")
                                .build()
                );
            } catch (Exception e) {
                return "账号创建失败";
            }

        }
        return JwtUtils.createToken(email);

//        return new SuccessResDTO("创建成功", new TokenDTO(JwtUtils.createToken(email)));

    }
}
