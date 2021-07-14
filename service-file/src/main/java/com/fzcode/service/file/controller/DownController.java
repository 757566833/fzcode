package com.fzcode.service.file.controller;

import com.fzcode.service.file.exception.CustomizeException;
import com.qiniu.common.QiniuException;
import com.qiniu.storage.DownloadUrl;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/download")
public class DownController {

    @GetMapping(value = "/file/{filename}")
    public String download(@PathVariable String filename) throws CustomizeException, QiniuException {
        DownloadUrl url = new DownloadUrl("blogoss", true, filename);
        return url.buildURL();
    }
   

}