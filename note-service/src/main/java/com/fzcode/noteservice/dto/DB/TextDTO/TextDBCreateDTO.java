package com.fzcode.noteservice.dto.DB.TextDTO;

import javax.validation.constraints.NotEmpty;

public class TextDBCreateDTO {

    @NotEmpty
    private String title;

    private String description;

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
