package com.fzcode.service.note.dto.DB.TextDTO;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class TextDBUpdateDTO {

    @NotNull
    private Integer tid;


    private String title;

    private String description;

    private String raw;

    private String html;

}
