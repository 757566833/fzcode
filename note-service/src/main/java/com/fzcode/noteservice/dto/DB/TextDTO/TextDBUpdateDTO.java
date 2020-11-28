package com.fzcode.noteservice.dto.DB.TextDTO;

import javax.validation.constraints.NotEmpty;

public class TextDBUpdateDTO {

    @NotEmpty
    private Integer nid;

    private String title;

    private String description;

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
}
