package com.fzcode.authservice.dto.request.list;

import com.fzcode.authservice.dto.common.PageDTO;
import lombok.Data;

import javax.persistence.Column;

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
