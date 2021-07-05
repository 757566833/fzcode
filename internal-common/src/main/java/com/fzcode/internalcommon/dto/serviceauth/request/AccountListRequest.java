package com.fzcode.internalcommon.dto.serviceauth.request;

import com.fzcode.internalcommon.dto.common.PageDTO;
import lombok.Data;

@Data
public class AccountListRequest  extends PageDTO {
    private String account;
    private Integer enabled;
    private Integer expired;
    private Integer locked;
    private Integer registerType;
    private Integer isDelete;

    private String username;
    private String githubUrl;
}
