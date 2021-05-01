package com.fzcode.noteservice.dto.elastic.TextDTO;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;

@Data
public class TextESPatchDTO {
    @NotEmpty
    private String id;
    private String title;
    private String subTitle;
    private String text;
    private ArrayList<String> categories;


    public TextESPatchDTO(@NotEmpty String id) {
        this.id = id;
    }



}
