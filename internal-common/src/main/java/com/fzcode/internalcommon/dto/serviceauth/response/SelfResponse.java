package com.fzcode.internalcommon.dto.serviceauth.response;

import lombok.Data;

import java.util.Date;

@Data
public class SelfResponse {

    private String aid;
    private Date createTime;
    private Date updateTime;
    private String updateBy;
    private String account;
    private Integer enabled ;
    private Integer expired ;
    private Integer locked ;
    private Integer registerType;
    private String deleteBy;
    private Integer isDelete ;
    private Date deleteTime;
    private String uid;
    private String username;
    private String avatar;
    private String idCard;
    private String githubUrl;
    private String blog;
}
