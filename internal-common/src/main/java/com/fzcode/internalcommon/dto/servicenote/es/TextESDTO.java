package com.fzcode.internalcommon.dto.servicenote.es;

import com.fzcode.internalcommon.crud.Create;
import com.fzcode.internalcommon.crud.Delete;
import com.fzcode.internalcommon.crud.FullUpdate;
import com.fzcode.internalcommon.crud.IncrementalUpdate;
import com.fzcode.internalcommon.crud.Retrieve;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
public class TextESDTO {
    @NotEmpty(groups = {IncrementalUpdate.class, FullUpdate.class, Delete.class, Retrieve.class})
    private Integer id;
    @NotEmpty(groups = {Create.class,FullUpdate.class})
    private String title;
    @NotEmpty(groups = {Create.class,FullUpdate.class})
    private String content;
    @NotEmpty(groups = {Create.class,FullUpdate.class})
    private List<Integer> categories;
    @NotEmpty(groups = {Create.class,FullUpdate.class})
    private Date createTime;
    @NotEmpty(groups = {Create.class,FullUpdate.class})
    private Date updateTime;
    @NotEmpty(groups = {Create.class,FullUpdate.class})
    private Integer createBy;
    @NotEmpty(groups = {Create.class,FullUpdate.class})
    private String summary;

    public TextESDTO(@NotEmpty Integer id, @NotEmpty String title) {
        this.id = id;
        this.title = title;
    }
}

