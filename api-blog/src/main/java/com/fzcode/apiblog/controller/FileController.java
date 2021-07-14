package com.fzcode.apiblog.controller;

import com.fzcode.apiblog.bean.MultipartInputStreamFileResource;
import com.fzcode.apiblog.config.Cache;
import com.fzcode.apiblog.config.Services;
import com.fzcode.apiblog.exception.CustomizeException;
import com.fzcode.internalcommon.dto.http.SuccessResponse;
import com.fzcode.internalcommon.dto.servicefile.request.Base64Upload;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;


import java.awt.image.DataBuffer;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


@RestController
@RequestMapping(value = "/file")
public class FileController {
    Services services;
    @Autowired
    public void setServices(Services services){
        this.services = services;
    }
    Cache cache;
    @Autowired
    public void setCache(Cache cache){
        this.cache = cache;
    }
    @PostMapping(value = "/test/upload/img/form")
    public SuccessResponse singleUpload(@RequestParam("file") MultipartFile file) throws CustomizeException {
        String filename = file.getOriginalFilename();
        String contentType = file.getContentType();
        if(contentType.indexOf("image")<0){
            throw new CustomizeException("不是图片");
        }
        LinkedMultiValueMap<String, MultipartFile> map = new LinkedMultiValueMap<>();
//        InputStream inputStream;
//        try {
//             inputStream =  file.getInputStream();
//        }catch (Exception e){
//            throw new CustomizeException("io出错");
//        }
        map.add("file",file);
        WebClient client = WebClient.create(services.getService().getFile().getHost());
        String filePath =  client.post()
                .uri("/upload/file")
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
    @GetMapping(value = "/download/{filename}")
    public ResponseEntity download(@PathVariable String filename) throws IOException {
        WebClient client = WebClient.create("https://blogoss.fzcode.com");

//
        Publisher<DataBuffer> dataBufferFlux =  client.get()
                .uri(filename)
                .exchange()
                .block()
                .bodyToFlux(DataBuffer.class);
//        OutputStream outputStream = new
        Path path = Paths.get(cache.getFile());
        AsynchronousFileChannel channel = AsynchronousFileChannel.open(path,StandardOpenOption.WRITE);
//        DataBufferUtils.write(dataBufferFlux,channel);
//        DataBufferUtils.write(dataBufferFlux, channel); //Creates new file or overwrites exisiting file
        HttpHeaders headers = new HttpHeaders();
        headers.setPragma("public");
        headers.setExpires(0);
        CacheControl cacheControl =  CacheControl.empty().mustRevalidate();
        headers.setCacheControl(cacheControl);
        ContentDisposition contentDisposition = ContentDisposition.builder("attachment").filename(filename).build();
        headers.setContentDisposition(contentDisposition);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity("filePath".getBytes(), headers, HttpStatus.OK);
    }
}
