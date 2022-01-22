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
    @NotEmpty(message = "tid:id不能为空",groups = {Retrieve.class, Delete.class})
    private Integer tid;
    @NotBlank(message = "title:标题不能为空",groups = {Create.class, FullUpdate.class})
    private String title;
    @NotBlank(message = "description:备注不能为空",groups = {Create.class, FullUpdate.class})
    private String description;
    @NotNull(message = "categories:分类不能为空",groups = {Create.class, FullUpdate.class})
    private List<Integer> categories;
    @NotNull(message = "type:类型不能为空",groups = {Create.class, FullUpdate.class})
    private Integer type;
    @NotNull(message = "value:值不能为空",groups = {Create.class, FullUpdate.class})
    private String value;
    @NotBlank(message = "content:内容不能为空",groups = {Create.class, FullUpdate.class})
    private String content;
}
