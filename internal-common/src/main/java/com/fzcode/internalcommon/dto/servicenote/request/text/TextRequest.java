package com.fzcode.internalcommon.dto.servicenote.request.text;

import com.fzcode.internalcommon.crud.Create;
import com.fzcode.internalcommon.crud.Delete;
import com.fzcode.internalcommon.crud.FullUpdate;
import com.fzcode.internalcommon.crud.IncrementalUpdate;
import com.fzcode.internalcommon.crud.Retrieve;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class TextRequest {
    // 改的功能id在patch中，不需要校验
    @NotEmpty(groups = {Retrieve.class, Delete.class})
    private Integer tid;
    @NotBlank(message = "标题不能为空",groups = {Create.class, FullUpdate.class})
    private String title;
    @NotBlank(message = "备注不能为空",groups = {Create.class, FullUpdate.class})
    private String description;
    @NotNull(message = "分类不能为空",groups = {Create.class, FullUpdate.class})
    private List<Integer> categories;
    @NotNull(message = "类型不能为空",groups = {Create.class, FullUpdate.class})
    private Integer type;
    private String raw;
    @NotBlank(message = "内容不能为空",groups = {Create.class, FullUpdate.class})
    private String text;
}
