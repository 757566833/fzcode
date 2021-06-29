package com.fzcode.api.blog.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class LoginRequest {
    @Email(message = "请输入正确的邮箱")
    private String email;
    @NotEmpty(message = "密码不能为空")
    private String password;

    @NotEmpty(message = "验证码不能为空")
    private String code;

}
