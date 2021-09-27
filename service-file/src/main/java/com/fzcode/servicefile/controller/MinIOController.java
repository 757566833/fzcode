package com.fzcode.servicefile.controller;

import com.fzcode.servicefile.exception.CustomizeException;
import io.minio.*;
import io.minio.errors.*;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping(value = "/minio")
public class MinIOController {
    MinioClient minioClient =  MinioClient.builder()
            .endpoint("http://127.0.0.1:9000")
              .credentials("blog", "blog_0811")
              .build();;
    @PostMapping(value = "/test/upload")
    public String upload(@RequestParam("file") MultipartFile file) throws  IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, ErrorResponseException {
        System.out.println(file.getName());
        String filename = file.getOriginalFilename();
        boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket("blog").build());
        if (!found) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket("blog").build());
        } else {
            System.out.println("Bucket 'blog' already exists.");
        }
        ObjectWriteResponse objectWriteResponse = minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket("blog")
                        .object(filename)
                        .stream(new ByteArrayInputStream(file.getBytes()), -1, 10485760)
                        .contentType(file.getContentType())
                        .build());
        return "http://192.168.31.158:30901/api/v1/buckets/blog/objects/download?preview=true&prefix=_images_logo.png&version_id=null";
    }
    @PostMapping(value = "/test")
    public String test() throws CustomizeException {
        System.out.println("发过来请求了");
        return "https://blogoss.fzcode.com/";
    }
}
