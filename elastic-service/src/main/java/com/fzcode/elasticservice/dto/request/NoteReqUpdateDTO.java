package com.fzcode.elasticservice.dto.request;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;

public class NoteReqUpdateDTO {
    @NotEmpty
    private Integer nid;
    @NotEmpty
    private String title;
    private String description;
    private String subTitle;
    private String text;
    private ArrayList<String> tags;

    public Integer getNid() {
        return nid;
    }

    public void setNid(Integer nid) {
        this.nid = nid;
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
