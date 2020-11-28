package com.fzcode.noteservice.dto.request.Text;

import javax.validation.constraints.NotEmpty;

public class TextReqDeleteDTO {
    @NotEmpty
    private Integer nid;


    public Integer getNid() {
        return nid;
    }

    public void setNid(Integer nid) {
        this.nid = nid;
    }

}
