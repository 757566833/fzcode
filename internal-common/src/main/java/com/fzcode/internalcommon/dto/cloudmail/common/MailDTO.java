package com.fzcode.internalcommon.dto.cloudmail.common;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
@Data
public class MailDTO {
    @NotEmpty(message = "邮箱不能为空")
    private String email;
}
