package com.fzcode.noteservice.dto.response;

import lombok.Data;

import java.util.ArrayList;

@Data
public class TextResDTO {

    private Integer id;
    private String title;
    private String description;
    private String subTitle;
    private String text;
    private ArrayList<String> tags;

    public TextResDTO(Integer id, String title, String description, String subTitle, String text, ArrayList<String> tags) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.subTitle = subTitle;
        this.text = text;
        this.tags = tags;
    }

}
