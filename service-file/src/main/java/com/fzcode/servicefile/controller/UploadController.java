package com.fzcode.servicefile.controller;

import com.alibaba.fastjson.JSON;
import com.fzcode.internalcommon.dto.servicefile.request.Base64Upload;
import com.fzcode.internalcommon.utils.FileUtils;
import com.fzcode.servicefile.config.QiNiuAuth;
import com.fzcode.servicefile.exception.CustomizeException;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Base64;
import java.util.Date;


@RestController
@RequestMapping(value = "/upload")
public class UploadController {

    @PostMapping(value = "/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String upload(@RequestParam("file") MultipartFile file) throws CustomizeException {
        UploadManager uploadManager = new UploadManager(QiNiuAuth.configuration);
        InputStream inputStream;
        try {
             inputStream =  file.getInputStream();
        }catch (IOException ioException){
            throw new CustomizeException("IO错误");
        }

        Response response = null;
        String filename = file.getOriginalFilename();
        String filePrefix = FileUtils.getFilePrefix(filename);
        String fileSuffix = FileUtils.getFileSuffix(filename);
        String time = String.valueOf(new Date().getTime());
        Double d = Math.random();
        String random = JSON.toJSONString(String.valueOf(d).replace(".",""));
        String serverFilename = filePrefix+random+time+"."+fileSuffix;
        System.out.println("serverFilename:"+serverFilename);
        try {
            response = uploadManager.put(inputStream,serverFilename.replaceAll("\"",""),QiNiuAuth.uploadToken,null, null);
        }catch (QiniuException ex){
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
        String str = null;
        try {
             str =  response.bodyString();
        }catch (QiniuException ex){
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }

        DefaultPutRet putRet = new Gson().fromJson(str, DefaultPutRet.class);
        return "https://blogoss.fzcode.com/"+putRet.key;
    }
    @PostMapping(value = "/base64", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String base64Upload(@RequestBody Base64Upload base64Upload) {
        UploadManager uploadManager = new UploadManager(QiNiuAuth.configuration);
        byte[] bytes = Base64.getDecoder().decode(base64Upload.getBase64().split(",")[1]);
        Response response = null;
        String filename = base64Upload.getFilename();
        String filePrefix = FileUtils.getFilePrefix(filename).replace(".","");
        String fileSuffix = FileUtils.getFileSuffix(filename);
        String time = String.valueOf(new Date().getTime());
        Double d = Math.random();
        String random = JSON.toJSONString(String.valueOf(d).replace(".",""));
        String serverFilename = filePrefix+random+time+"."+fileSuffix;
        try {
            response = uploadManager.put(bytes,serverFilename.replaceAll("\"",""),QiNiuAuth.uploadToken);
        }catch (QiniuException ex){
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }

        String str = null;
        try {
            str =  response.bodyString();
        }catch (QiniuException ex){
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }

        DefaultPutRet putRet = new Gson().fromJson(str, DefaultPutRet.class);
        return "https://blogoss.fzcode.com/"+putRet.key;
    }
}