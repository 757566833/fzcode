package com.fzcode.mailservice.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class MailDTO {

    @NotEmpty(message = "邮箱不能为空")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
