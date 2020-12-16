package com.fzcode.noteservice.DBInterface;

import lombok.Data;

import java.util.List;

public interface TextDBGetDTO {
    String getAvatar();

    Integer getCid();

    Integer getCreate_by();

    String getCreate_time();

    String getCTitle();

    String getDescription();

    String getTags();

    String getText();

    Integer getTid();

    String getTitle();

    Integer getUpdate_by();

    String getUpdate_time();

    String getUsername();
}
