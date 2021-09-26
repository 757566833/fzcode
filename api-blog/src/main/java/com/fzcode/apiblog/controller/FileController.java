package com.fzcode.apiblog.controller;

import com.fzcode.apiblog.config.Services;
import com.fzcode.apiblog.exception.CustomizeException;
import com.fzcode.internalcommon.dto.http.SuccessResponse;
import com.fzcode.internalcommon.dto.servicefile.request.Base64Upload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping(value = "/file")
public class FileController {
    Services services;
    @Autowired
    public void setServices(Services services){
        this.services = services;
    }
    WebClient client;
    @PostConstruct
    public void init (){
        this.client = WebClient.create(services.getService().getFile().getHost());
    }
    @PostMapping(value = "/test/upload/img/form")
    public SuccessResponse singleUpload(@RequestParam("file") MultipartFile file) throws CustomizeException {
        String filename = file.getOriginalFilename();
        String contentType = file.getContentType();
        if(contentType.indexOf("image")<0){
            throw new CustomizeException("不是图片");
        }
        System.out.println(file);
        LinkedMultiValueMap<String, MultipartFile> map = new LinkedMultiValueMap<>();
        map.add("file",file);
        String filePath =  this.client.post()
                .uri("/minio/test")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(map))
                .exchange()
                .block()
                .bodyToMono(String.class)
//                .bodyToMono(new TypeReference<ListResponseDTO<TextResponse>>(){}.getType())
                .block();
        return new SuccessResponse("查询成功", filePath);
    }
    @PostMapping(value = "/test/upload/img/base64")
    public SuccessResponse base64Upload(@RequestBody Base64Upload base64Upload) throws CustomizeException {
        WebClient client = WebClient.create(services.getService().getFile().getHost());
        String filePath =  client.post()
                .uri("/upload/base64")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(base64Upload)
                .exchange()
                .block()
                .bodyToMono(String.class)
//                .bodyToMono(new TypeReference<ListResponseDTO<TextResponse>>(){}.getType())
                .block();
        return new SuccessResponse("查询成功", filePath);
    }
}
