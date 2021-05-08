package com.fzcode.noteservice.utils;


public class HtmlUtils {
    public static String html2Text(String html){
        String txtcontent = html.replaceAll("</?[^>]+>", ""); //剔出<html>的标签
        txtcontent = txtcontent.replaceAll("<a>\\s*|\t|\r|\n</a>", "");//去除字符串中的空格,回车,换行符,制表符
        return txtcontent;
    }

}
