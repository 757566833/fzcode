package com.fzcode.noteservice.dto.elastic.TextDTO;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;

public class TextESUpdateDTO {
    @NotEmpty
    private String id;
    @NotEmpty
    private String title;
    private String subTitle;
    private String text;
    private ArrayList<String> tags;


    public TextESUpdateDTO(@NotEmpty String id, @NotEmpty String title) {
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

}
