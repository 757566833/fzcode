package com.fzcode.internalcommon.dto.common;

import com.fzcode.internalcommon.ifs.entity.IUsers;
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
}
