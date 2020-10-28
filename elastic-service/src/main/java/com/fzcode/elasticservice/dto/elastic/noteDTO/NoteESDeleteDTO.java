package com.fzcode.elasticservice.dto.elastic.noteDTO;

import javax.validation.constraints.NotEmpty;

public class NoteESDeleteDTO {
    @NotEmpty
    private String id;

    public NoteESDeleteDTO(@NotEmpty String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
