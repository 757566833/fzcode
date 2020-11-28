package com.fzcode.noteservice.dto.elastic.TextDTO;

import javax.validation.constraints.NotEmpty;

public class TextESDeleteDTO {
    @NotEmpty
    private String id;

    public TextESDeleteDTO(@NotEmpty String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
