package com.fzcode.noteservice.dto.response;

import java.util.ArrayList;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
