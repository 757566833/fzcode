package com.fzcode.internalcommon.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class FileUtils {
    public static boolean isImage(File file){
        try {
            BufferedImage bi = ImageIO.read(file);
            if (bi == null) {
                return false;
            }
        }catch (Exception e){
            return false;
        }


        return true;
    }
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
