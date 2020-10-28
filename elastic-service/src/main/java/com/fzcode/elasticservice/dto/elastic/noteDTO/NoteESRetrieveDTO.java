package com.fzcode.elasticservice.dto.elastic.noteDTO;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;

public class NoteESRetrieveDTO {
    @NotEmpty
    private String id;

    public NoteESRetrieveDTO(@NotEmpty String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
