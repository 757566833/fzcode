package com.fzcode.noteservice.dto.elastic.TextDTO;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;

@Data
public class TextESUpdateDTO {
    @NotEmpty
    private String id;
    @NotEmpty
    private String title;
    private String subTitle;
    private String text;
    private ArrayList<String> categories;


    public TextESUpdateDTO(@NotEmpty String id, @NotEmpty String title) {
        this.id = id;
        this.title = title;
    }


}
