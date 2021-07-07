package com.fzcode.internalcommon.dto.servicenote.request.text;

import com.fzcode.internalcommon.dto.common.PageDTO;
import lombok.Data;

import java.util.List;

@Data
public class TextGetListRequest extends PageDTO {
    private String title;
    private List<Integer> categories;
}
