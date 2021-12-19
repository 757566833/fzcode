package com.fzcode.internalcommon.dto.servicenote.request.text;

import com.fzcode.internalcommon.dto.common.ListRequestDTO;
import lombok.Data;

@Data
public class SelfListRequest extends ListRequestDTO {
    private String search;
}
