package com.fzcode.service.note.dto.request.Text;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;

@Data
public class TextReqPatchDTO {
    @NotEmpty
    private Integer tid;

    private String title;
    private String description;
    private String html;
    private String raw;
    private ArrayList<Integer> categories;

}
