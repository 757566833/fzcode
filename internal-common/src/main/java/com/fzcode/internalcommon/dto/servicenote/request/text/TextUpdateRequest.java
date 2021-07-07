package com.fzcode.internalcommon.dto.servicenote.request.text;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;

@Data
public class TextUpdateRequest {
    @NotEmpty
    private Integer tid;
    @NotBlank
    private String title;
    private String description;
    private String html;
    private String raw;
    private ArrayList<Integer> categories;


}
