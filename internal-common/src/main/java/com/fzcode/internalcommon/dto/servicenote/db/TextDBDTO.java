package com.fzcode.internalcommon.dto.servicenote.db;

import com.fzcode.internalcommon.crud.Create;
import com.fzcode.internalcommon.crud.Delete;
import com.fzcode.internalcommon.crud.FullUpdate;
import com.fzcode.internalcommon.crud.IncrementalUpdate;
import com.fzcode.internalcommon.crud.Retrieve;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class TextDBDTO {
    @NotNull(groups = {IncrementalUpdate.class, FullUpdate.class, Delete.class, Retrieve.class})
    private Integer tid;
    @NotEmpty(groups = {Create.class,FullUpdate.class})
    private String title;
    @NotEmpty(groups = {Create.class,FullUpdate.class})
    private String description;
    @NotEmpty(groups = {Create.class,FullUpdate.class})
    private String raw;
    @NotEmpty(groups = {Create.class,FullUpdate.class})
    private String html;
}
