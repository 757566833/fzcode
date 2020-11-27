package com.fzcode.elasticservice.dto.request;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;

public class NoteReqDeleteDTO {
    @NotEmpty
    private Integer nid;


    public Integer getNid() {
        return nid;
    }

    public void setNid(Integer nid) {
        this.nid = nid;
    }

}
