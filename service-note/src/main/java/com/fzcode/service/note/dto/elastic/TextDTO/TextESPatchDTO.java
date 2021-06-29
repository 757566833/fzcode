package com.fzcode.service.note.dto.elastic.TextDTO;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;

@Data
public class TextESPatchDTO {
    @NotEmpty
    private String id;
    private String title;
    private String text;
    private ArrayList<Integer> categories;

    public TextESPatchDTO(@NotEmpty String id) {
        this.id = id;
    }



}
