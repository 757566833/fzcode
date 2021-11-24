package com.fzcode.internalcommon.dto.servicenote.request.note;

import com.fzcode.internalcommon.dto.common.ListRequestDTO;
import lombok.Data;

@Data
public class SearchRequest extends ListRequestDTO {
    String search;
}
