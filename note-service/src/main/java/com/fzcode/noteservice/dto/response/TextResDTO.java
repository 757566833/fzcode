package com.fzcode.noteservice.dto.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TextResDTO {

    private Integer id;
    private String title;
    private String description;
    private String text;
    private List<String> tags;

    public TextResDTO(Integer id, String title, String description, String text, List<String> tags) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.text = text;
        this.tags = tags;
    }

}
