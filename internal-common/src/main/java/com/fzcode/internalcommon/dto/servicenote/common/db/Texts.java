package com.fzcode.internalcommon.dto.servicenote.common.db;
import com.fzcode.internalcommon.dto.servicenote.ifs.ITexts;
import lombok.Data;
import java.util.Date;

@Data
public class Texts implements ITexts {
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
}
