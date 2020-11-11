package com.fzcode.fileservice.controller;

import com.fzcode.fileservice.bean.FileBean;
import com.fzcode.fileservice.config.Config;
import com.fzcode.fileservice.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.Part;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


@RestController
@RequestMapping(value = "/file")
public class FileController {
    @Autowired
    private Config config;

    @GetMapping(value = "/getFile")
    public Mono<String> getFile() {

        return Mono.just("file");
    }

    @PostMapping(value = "/postForm", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Mono<String> postForm(ServerWebExchange serverWebExchange) {
        Mono<MultiValueMap<String, String>> multipartData = serverWebExchange.getFormData();

        return multipartData.map(formData -> {
            String parameterValue = formData.getFirst("ttt");
            System.out.println(formData.toString());
            System.out.println(formData.toSingleValueMap());
            System.out.println(parameterValue);
            return parameterValue;
        });
    }

    @PostMapping(value = "/formData/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<String> upload(ServerWebExchange serverWebExchange) {
        Mono<MultiValueMap<String, Part>> multipartData = serverWebExchange.getMultipartData();
        return multipartData.flatMap(multiValueMap -> {
            System.out.println(multiValueMap);
            Part part = multiValueMap.getFirst("file");
            String fileName = part.headers().getContentDisposition().getFilename();
            String preFix = FileUtil.getFilePrefix(fileName);
            String suffix = FileUtil.getFileSuffix(fileName);
            FileBean fileBean = new FileBean();
            fileBean.setPreFix(preFix);
            fileBean.setSuffix(suffix);
            fileBean.setPart(part);
            Path tempFile = null;
            try {
                tempFile = Files.createTempFile(Paths.get(config.getFilePath()), fileBean.getPreFix(), fileBean.getSuffix());
            } catch (IOException e) {
                e.printStackTrace();
            }
            AsynchronousFileChannel channel = null;
            try {
                channel = AsynchronousFileChannel.open(tempFile, StandardOpenOption.WRITE);
            } catch (IOException e) {
                e.printStackTrace();
            }
            final AsynchronousFileChannel currentChannel = channel;
            final  Path currentTempFile = tempFile;
            Mono<String> m = Mono.create(stringMonoSink -> {
                Flux<DataBuffer> flux = DataBufferUtils.write(fileBean.getPart().content(), currentChannel, 0).doOnComplete(() -> {
                    System.out.println("结束");
                    stringMonoSink.success(currentTempFile.toString());
                });
                flux.subscribe(System.out::println);
            });


            return m;
        });

//        return multipartData.map(multiValueMap -> {
//            Part part = multiValueMap.getFirst("file");
//            System.out.println(part.name());
//            System.out.println(part.headers());
//
//            String fileName = part.headers().getContentDisposition().getFilename();
//            System.out.println(fileName);
//            String preFix = FileUtil.getFilePrefix(fileName);
//            String suffix = FileUtil.getFileSuffix(fileName);
//
//            System.out.println(config.getFilePath());
//            Path tempFile = null;
//            try {
//                tempFile = Files.createTempFile(Paths.get(config.getFilePath()), preFix, suffix);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            AsynchronousFileChannel channel = null;
//            try {
//                channel = AsynchronousFileChannel.open(tempFile, StandardOpenOption.WRITE);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            DataBufferUtils.write(part.content(), channel, 0).doOnComplete(() -> {
//                System.out.println("结束");
//            }).subscribe();
//            return tempFile;
//        }).map(tempFile -> {
//            return tempFile.toString();
//        });
    }
}