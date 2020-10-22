package com.fzcode.authservice.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class ResetDTO {
    @NotEmpty(message = "旧密码不能为空")
    private String oldPassword;
    @NotEmpty(message = "新密码不能为空")
    private String newPassword;

    public ResetDTO(@Email(message = "旧密码不能为空") String oldPassword, @NotEmpty(message = "新密码不能为空") String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
