package com.fzcode.noteservice.dto.request.Text;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
public class TextReqCreateDTO {

    @NotEmpty
    private String title;
    private String description;
    private String subTitle;
    private String text;
    @NotNull
    private Integer cid;
    private List<String> categories;
}
