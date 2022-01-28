package com.fzcode.internalcommon.dto.servicenote.response.text;

import com.fzcode.internalcommon.ifs.entity.ICategories;
import com.fzcode.internalcommon.ifs.entity.ITexts;
import com.fzcode.internalcommon.ifs.entity.IUsers;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TextResponse implements ITexts, IUsers, ICategories {
    private Integer tid;
    private Date createTime;
    private Date updateTime;
    private String createBy;
    private String updateBy;
    private String title;
    private String description;
    private String value;
    private Integer isDelete = 0;
    private Integer top = 0;
    private Integer type;

    private String uid;
    private String username;
    private String avatar;
    private String idCard;
    private String githubUrl;
    private String blog;
    private String aid;
    private List<ICategories> categories;
}
