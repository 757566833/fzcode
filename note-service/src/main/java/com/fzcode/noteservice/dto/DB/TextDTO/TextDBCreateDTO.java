package com.fzcode.noteservice.dto.DB.TextDTO;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class TextDBCreateDTO {

    @NotEmpty
    private String title;

    private String description;

}
