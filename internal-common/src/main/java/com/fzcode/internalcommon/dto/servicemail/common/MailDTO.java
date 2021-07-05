package com.fzcode.internalcommon.dto.servicemail.common;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
@Data
public class MailDTO {
    @NotEmpty(message = "邮箱不能为空")
    private String email;
}
