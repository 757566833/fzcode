package com.fzcode.servicenote.repositroy.mapper;

public interface TextDBGetByIdMapper {
    Integer getCreate_by();

    String getUpdate_time();

    String getTitle();

    String getHtml();
    String getRaw();

    String getAvatar();

    String getUsername();

    String getGithub_url();
    String getType();
    String getText();
}
