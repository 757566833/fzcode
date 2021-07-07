package com.fzcode.internalcommon.dto.servicenote.request.category;

import com.fzcode.internalcommon.dto.common.PageDTO;
import lombok.Data;

import java.util.List;

@Data
public class CategoryGetListRequest extends PageDTO {
    private String title;
    private List<Integer> categories;
}
