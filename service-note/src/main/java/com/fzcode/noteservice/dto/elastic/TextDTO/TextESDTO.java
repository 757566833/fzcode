package com.fzcode.noteservice.dto.elastic.TextDTO;

import lombok.Data;

import java.util.ArrayList;

@Data
public class TextESDTO {

    private String id;

    private String title;
    //    @NotEmpty
    private String subTitle;
    //    @NotEmpty
    private String text;
    //    @NotEmpty
    private ArrayList<Integer> categories;
    private Boolean isDelete ;

    public TextESDTO(String id, String title, String subTitle, String text, ArrayList<Integer> categories) {
        this.id = id;
        this.title = title;
        this.subTitle = subTitle;
        this.text = text;
        this.categories = categories;
    }
    public TextESDTO() {
    }
}
