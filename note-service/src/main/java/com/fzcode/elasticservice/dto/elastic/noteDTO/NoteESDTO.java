package com.fzcode.elasticservice.dto.elastic.noteDTO;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;

public class NoteESDTO {

    private String id;

    private String title;
    //    @NotEmpty
    private String subTitle;
    //    @NotEmpty
    private String text;
    //    @NotEmpty
    private ArrayList<String> tags;
    private Boolean isDelete ;

    public NoteESDTO(String id, String title, String subTitle, String text, ArrayList<String> tags) {
        this.id = id;
        this.title = title;
        this.subTitle = subTitle;
        this.text = text;
        this.tags = tags;
    }
    public NoteESDTO() {

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
