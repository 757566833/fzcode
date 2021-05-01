package com.fzcode.noteservice.dto.elastic.TextDTO;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class TextESRetrieveDTO {
    @NotEmpty
    private String id;

    public TextESRetrieveDTO(@NotEmpty String id) {
        this.id = id;
    }

}
