package com.fzcode.internalcommon.dto.apiblog.response;

import com.fzcode.internalcommon.ifs.entity.IUsers;
import com.fzcode.internalcommon.ifs.entity.ITexts;
import lombok.Data;

import java.util.Date;

@Data
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
    private Integer tid ;
    private Date createTime ;
    private String createBy ;
    private String title ;
    private String description ;
    private String value ;
    private Integer isDelete;
    private Integer top ;
    private Integer type ;
}
