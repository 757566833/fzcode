package com.fzcode.servicenote.dto.elastic.TextDTO;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
@Data
public class TextESCreateDTO {
    @NotEmpty
    private String id;
    @NotEmpty
    private String title;
    //    @NotEmpty
    private String text;
    //    @NotEmpty
    private List<Integer> categories;

    public TextESCreateDTO(@NotEmpty String id, @NotEmpty String title) {
        this.id = id;
        this.title = title;
    }



}
