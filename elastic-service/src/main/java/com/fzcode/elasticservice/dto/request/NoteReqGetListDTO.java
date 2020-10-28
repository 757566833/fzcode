package com.fzcode.elasticservice.dto.request;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;

public class NoteReqGetListDTO {

    @NotEmpty
    private int page;
    @NotEmpty
    private int size;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
