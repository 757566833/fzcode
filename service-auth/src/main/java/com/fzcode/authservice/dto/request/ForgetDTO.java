package com.fzcode.authservice.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class ForgetDTO {
    @Email(message = "请输入正确的邮箱")
    private String email;
    @NotEmpty(message = "密码不能为空")
    private String password;
    @NotEmpty(message = "验证码")
    private String code;

    public ForgetDTO(@Email(message = "请输入正确的邮箱") String email, @NotEmpty(message = "密码不能为空") String password, @NotEmpty(message = "验证码") String code) {
        this.email = email;
        this.password = password;
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
