package com.fzcode.elasticservice.dto.DB.NoteDTO;

import javax.validation.constraints.NotEmpty;

public class NoteDBRetrieveDTO {

    private Integer nid;

    public Integer getNid() {
        return nid;
    }

    public void setNid(Integer nid) {
        this.nid = nid;
    }
}
