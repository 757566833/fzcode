package com.fzcode.service.auth.dto.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class RegisterDTO {
    @Email(message = "请输入正确的邮箱")
    private String email;
    @NotEmpty(message = "密码不能为空")
    private String password;

    @NotEmpty(message = "验证码")
    private String code;

    @NotNull(message = "注册方式未明")
    private Integer registerType;

    public RegisterDTO(@Email(message = "请输入正确的邮箱") String email, @NotEmpty(message = "密码不能为空") String password,  Integer registerType,String code) {
        this.email = email;
        this.password = password;
        this.registerType = registerType;
        this.code = code;
    }


}
