package com.fzcode.noteservice.dto.request.Text;

import lombok.Data;

import java.util.ArrayList;

@Data
public class TextReqPatchDTO {


    private String title;
    private String description;
    private String subTitle;
    private String text;
    private ArrayList<String> categories;

}
