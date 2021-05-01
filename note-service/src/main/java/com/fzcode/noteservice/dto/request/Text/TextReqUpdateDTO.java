package com.fzcode.noteservice.dto.request.Text;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
@Data
public class TextReqUpdateDTO {

    @NotEmpty
    private String title;
    private String description;
    private String subTitle;
    private String text;
    private ArrayList<String> categories;


}
