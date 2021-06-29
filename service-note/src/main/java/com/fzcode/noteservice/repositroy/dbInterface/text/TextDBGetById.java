package com.fzcode.noteservice.repositroy.dbInterface.text;

public interface TextDBGetById {
    Integer getCreate_by();

    String getUpdate_time();

    String getTitle();

    String getHtml();
    String getRaw();

    String getAvatar();

    String getUsername();

    String getGithub_url();
}
