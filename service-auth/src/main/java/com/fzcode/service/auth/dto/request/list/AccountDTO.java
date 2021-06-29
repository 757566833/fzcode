package com.fzcode.service.auth.dto.request.list;

import com.fzcode.service.auth.dto.common.PageDTO;
import lombok.Data;

@Data
public class AccountDTO extends PageDTO {
    private String account;
    private Integer enabled;
    private Integer expired;
    private Integer locked;
    private Integer registerType;
    private Integer isDelete;

    private String username;
    private String githubUrl;
}
