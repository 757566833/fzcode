package com.fzcode.internalcommon.dto.apiblog.response;

import com.fzcode.internalcommon.dto.serviceauth.ifs.IUsers;
import com.fzcode.internalcommon.dto.servicenote.ifs.ITexts;

import java.util.Date;

public class TextDetailDTO implements ITexts, IUsers {
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
