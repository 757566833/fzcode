package com.fzcode.elasticservice.dto.elastic.noteDTO;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;

public class NoteESCreateDTO {
    @NotEmpty
    private String id;
    @NotEmpty
    private String title;
    //    @NotEmpty
    private String subTitle;
    //    @NotEmpty
    private String text;
    //    @NotEmpty
    private ArrayList<String> tags;

    private Boolean isDelete = false;

    public NoteESCreateDTO(@NotEmpty String id, @NotEmpty String title) {
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

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

}
