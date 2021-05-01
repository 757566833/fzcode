package com.fzcode.noteservice.dto.elastic.TextDTO;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class TextESDeleteDTO {
    @NotEmpty
    private String id;

    public TextESDeleteDTO(@NotEmpty String id) {
        this.id = id;
    }

}
