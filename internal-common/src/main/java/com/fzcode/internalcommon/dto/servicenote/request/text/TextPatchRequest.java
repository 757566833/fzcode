package com.fzcode.internalcommon.dto.servicenote.request.text;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;

@Data
public class TextPatchRequest {
    @NotEmpty
    private Integer tid;

    private String title;
    private String description;
    private String html;
    private String raw;
    private ArrayList<Integer> categories;
}
