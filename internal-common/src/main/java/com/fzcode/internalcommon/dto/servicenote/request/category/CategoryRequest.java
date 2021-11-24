package com.fzcode.internalcommon.dto.servicenote.request.category;

import com.fzcode.internalcommon.crud.Create;
import com.fzcode.internalcommon.crud.Delete;
import com.fzcode.internalcommon.crud.FullUpdate;
import com.fzcode.internalcommon.crud.IncrementalUpdate;
import com.fzcode.internalcommon.crud.Retrieve;
import com.fzcode.internalcommon.crud.RetrieveList;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CategoryRequest {
    @NotNull(message = "id不能为空",groups = {Delete.class,IncrementalUpdate.class, FullUpdate.class, Retrieve.class})
    private Integer cid;
    @NotEmpty(message = "标题不能为空",groups = { FullUpdate.class, Create.class})
    private String title;
    @NotEmpty(message = "备注不能为空",groups = { FullUpdate.class, Create.class})
    private String description;
    private String img;
    private String detail;
}
