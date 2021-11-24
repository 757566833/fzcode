package com.fzcode.servicenote.dto.elastic.TextDTO;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Data
public class TextESUpdateDTO {
    @NotEmpty
    private String id;
    @NotEmpty
    private String title;
    private String text;
    private List<Integer> categories;


    public TextESUpdateDTO(@NotEmpty String id, @NotEmpty String title) {
        this.id = id;
        this.title = title;
    }


}
