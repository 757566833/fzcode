package com.fzcode.fileservice.bean;

import org.springframework.http.codec.multipart.Part;

public class FileBean {
    private String preFix;
    private String suffix;
    private Part part;

    public String getPreFix() {
        return preFix;
    }

    public void setPreFix(String preFix) {
        this.preFix = preFix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }
}
