package com.fzcode.noteservice.dto.DB.TextDTO;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class TextDBDeleteDTO {

    @NotNull
    private Integer tid;
}
