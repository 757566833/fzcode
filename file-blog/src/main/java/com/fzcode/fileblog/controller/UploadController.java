package com.fzcode.fileblog.controller;

import com.fzcode.internalcommon.dto.fileblog.request.Base64Upload;
import com.fzcode.internalcommon.exception.CustomizeException;
import com.fzcode.internalcommon.utils.FileUtils;
import com.fzcode.fileblog.config.QiNiuAuth;
import com.fzcode.internalcommon.utils.JSONUtils;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Date;


@RestController
@RequestMapping(value = "/upload")
public class UploadController {

    @PostMapping(value = "/test/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String upload(@RequestParam("file") MultipartFile file) throws CustomizeException {
        System.out.println("进入旧版方法");
        UploadManager uploadManager = new UploadManager(QiNiuAuth.configuration);
        InputStream inputStream;
        try {
             inputStream =  file.getInputStream();
        }catch (IOException ioException){
            throw new CustomizeException(HttpStatus.INTERNAL_SERVER_ERROR,"IO错误");
        }

        Response response = null;
        String filename = file.getOriginalFilename();
        String filePrefix = FileUtils.getFilePrefix(filename);
        String fileSuffix = FileUtils.getFileSuffix(filename);
        String time = String.valueOf(new Date().getTime());
        Double d = Math.random();
//        String random = JSON.toJSONString(String.valueOf(d).replace(".",""));
        String random = JSONUtils.stringify(String.valueOf(d).replace(".",""));
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

        DefaultPutRet putRet = JSONUtils.parse(str,DefaultPutRet.class);
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
        String random = JSONUtils.stringify(String.valueOf(d).replace(".",""));
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

        DefaultPutRet putRet = JSONUtils.parse(str,DefaultPutRet.class);
        return "https://blogoss.fzcode.com/"+putRet.key;
    }
}