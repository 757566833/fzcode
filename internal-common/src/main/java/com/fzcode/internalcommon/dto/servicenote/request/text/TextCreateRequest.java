package com.fzcode.internalcommon.dto.servicenote.request.text;

import com.fzcode.internalcommon.constant.TextTypeEnum;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class TextCreateRequest {
    @NotEmpty(message = "标题不能为空")
    private String title;
    @NotEmpty(message = "备注不能为空")
    private String description;
    @NotEmpty(message = "html不能为空")
    private String html;
    @NotNull(message = "分类不能为空")
    private List<Integer> categories;
    @NotEmpty(message = "类型不能为空")
    private TextTypeEnum type;
    private String raw;
    private String text;
}
