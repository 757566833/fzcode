package com.fzcode.service.file.util;

public class FileUtil {
    /**
     * 获取文件前缀
     *
     * @param fileName
     * @return
     */
    public static String getFilePrefix(String fileName) {
        if(fileName == null || "".equals(fileName)){
            return null;
        }
        return fileName.substring(0, fileName.lastIndexOf("."));
    }

    /**
     * 获取文件后缀
     *
     * @param fileName
     *            文件名称
     * @return
     */
    public static String getFileSuffix(String fileName) {
        if(fileName == null || "".equals(fileName)){
            return null;
        }
        return fileName.substring(fileName.lastIndexOf(".")+1);//从最后一个点之后截取字符串
    }
}
