package com.fzcode.fileblog.controller;

import com.fzcode.fileblog.config.Minio;
import com.fzcode.fileblog.exception.CustomizeException;
import com.fzcode.internalcommon.utils.FileUtils;
import io.minio.*;
import io.minio.messages.Tags;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "文件模块")
@RestController
@RequestMapping(value = "/io")
public class MinIOController {
    Minio minio;
    @Autowired
    public void  setMinio(Minio minio){
        this.minio = minio;
    }
    MinioClient minioClient;
    @PostConstruct
    public void initMinioClient(){
        minioClient = MinioClient.builder()
                .endpoint(this.minio.getEndpoint())
                .credentials(this.minio.getAccessKey(), this.minio.getSecretKey())
                .build();
    }
    @ApiOperation(value = "测试接口")
    @GetMapping(value = "/test")
    public String test (){
        String ipHostAddress = "";
        try {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress ip = (InetAddress) addresses.nextElement();
                    if (ip instanceof Inet4Address
                            && !ip.isLoopbackAddress() //loopback地址即本机地址，IPv4的loopback范围是127.0.0.0 ~ 127.255.255.255
                            && !ip.getHostAddress().contains(":")) {
                        System.out.println("本机的IP = " + ip.getHostAddress());
                        ipHostAddress = ip.getHostAddress();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "file-blog:"+ipHostAddress;

    }
    @ApiOperation(value = "上传接口")
    @PostMapping(value = "/test/upload")
    public String upload(@RequestParam("file") MultipartFile file) throws CustomizeException {
        String name = file.getOriginalFilename();
        String suffix =  FileUtils.getFileSuffix(name);
        String type = file.getContentType();
        BigInteger bigInt = null;
        try(InputStream inputStream = file.getInputStream()){
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[1024];
            int length = -1;
            while ((length = inputStream.read(buffer, 0, 1024)) != -1) {
                md.update(buffer, 0, length);
            }
            bigInt = new BigInteger(1, md.digest());
        } catch (Exception e) {
            throw new CustomizeException("md5出错");
        }
        boolean found;
        try {
             found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(this.minio.getBucket()).build());
        } catch (Exception e) {
            throw  new CustomizeException("无效参数");
        }
        String filename = bigInt.toString()+"."+suffix;
        if (!found) {
            try {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(this.minio.getBucket()).build());
            }catch (Exception e){
                throw  new CustomizeException("创建bucket异常");
            }
        }
        byte[] bytes;
        try {
            bytes = file.getBytes();
        }catch (Exception e){
            throw  new CustomizeException("文件异常");
        }
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(this.minio.getBucket())
                            .object(filename)
                            .stream(new ByteArrayInputStream(bytes), -1, 10485760)
                            .contentType(file.getContentType())
                            .build());
        }catch (Exception e){
            throw  new CustomizeException("存储异常");
        }
        try {
            Map<String, String> map = new HashMap<>();
            map.put("type", type);
            minioClient.setObjectTags(
                    SetObjectTagsArgs.builder()
                            .bucket(this.minio.getBucket())
                            .object(filename)
                            .tags(map)
                            .build());
        }catch (Exception e){
            throw  new CustomizeException("设置类型异常");
        }
        return "/file/blog/io/test/"+filename;
    }

    @ApiOperation(value = "获取接口")
    @GetMapping(value = "/test/{filename}")
    public ResponseEntity preview(@PathVariable String filename,@RequestParam(name = "action", defaultValue = "preview") String action) throws CustomizeException {
        InputStream stream;
        System.out.println(action);
        try {
             stream =minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(this.minio.getBucket())
                            .object(filename)
                            .build());
        }catch (Exception e){
            throw  new CustomizeException("读取异常");
        }
        MediaType mediaType= MediaType.APPLICATION_OCTET_STREAM;;
        if(!action.equals("download")){
            try {
                Tags tags = minioClient.getObjectTags(
                        GetObjectTagsArgs.builder().bucket(this.minio.getBucket()).object(filename).build());
                Map<String, String> map = tags.get();
                String type = map.get("type");
                if(type!=null){
                    mediaType = MediaType.parseMediaType(type);
                }
            }catch (Exception e){

            }
        }
        InputStreamResource inputStreamResource = new InputStreamResource(stream);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(mediaType);
        return new ResponseEntity(inputStreamResource, httpHeaders, HttpStatus.OK);
    }
}
