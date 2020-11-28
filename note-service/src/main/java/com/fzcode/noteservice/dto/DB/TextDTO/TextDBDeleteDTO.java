package com.fzcode.noteservice.dto.DB.TextDTO;

import javax.validation.constraints.NotEmpty;

public class TextDBDeleteDTO {

    @NotEmpty
    private Integer nid;

    public Integer getNid() {
        return nid;
    }

    public void setNid(Integer nid) {
        this.nid = nid;
    }
}
