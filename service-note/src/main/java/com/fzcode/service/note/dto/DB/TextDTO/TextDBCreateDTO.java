package com.fzcode.service.note.dto.DB.TextDTO;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class TextDBCreateDTO {

    @NotEmpty
    private String title;
    @NotEmpty
    private String description;
    @NotEmpty
    private String raw;
    @NotEmpty
    private String html;
}
