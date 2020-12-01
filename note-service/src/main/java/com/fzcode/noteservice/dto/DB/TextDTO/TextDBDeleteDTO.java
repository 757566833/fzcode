package com.fzcode.noteservice.dto.DB.TextDTO;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class TextDBDeleteDTO {

    @NotNull
    private Integer nid;

    public Integer getNid() {
        return nid;
    }

    public void setNid(Integer nid) {
        this.nid = nid;
    }
}
