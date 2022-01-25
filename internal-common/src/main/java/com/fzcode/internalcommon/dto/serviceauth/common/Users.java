package com.fzcode.internalcommon.dto.serviceauth.common;

import com.fzcode.internalcommon.dto.serviceauth.ifs.IUsers;
import lombok.Data;

import java.util.Date;

@Data
public class Users implements IUsers {
    private String uid;
    private Date updateTime;
    private String updateBy;
    private String username;
    private String avatar;
    private String idCard;
    private String githubUrl;
    private String blog;
    private String aid;
    private Integer tid;
    private Date createTime;
    private String createBy;
    private String title;
    private String description;
    private String value;
    private Integer isDelete = 0;
    private Integer top = 0;
    private Integer type;
}
