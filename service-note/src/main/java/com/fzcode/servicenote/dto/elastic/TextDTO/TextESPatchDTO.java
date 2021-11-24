package com.fzcode.servicenote.dto.elastic.TextDTO;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Data
public class TextESPatchDTO {
    @NotEmpty
    private String id;
    private String title;
    private String text;
    private List<Integer> categories;

    public TextESPatchDTO(@NotEmpty String id) {
        this.id = id;
    }



}
