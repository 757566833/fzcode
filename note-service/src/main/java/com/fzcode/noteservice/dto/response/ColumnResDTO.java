package com.fzcode.noteservice.dto.response;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@Data
public class ColumnResDTO {

    private String cid;
    private String title;
    private String description;
    private String img;
    private String detail;

    public ColumnResDTO(String cid, String title, String description, String img, String detail) {
        this.cid = cid;
        this.title = title;
        this.description = description;
        this.img = img;
        this.detail = detail;
    }

}
