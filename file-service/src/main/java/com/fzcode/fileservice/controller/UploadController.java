package com.fzcode.fileservice.controller;

import com.fzcode.fileservice.config.Config;
import com.fzcode.fileservice.dto.Base64DTO;
import com.fzcode.fileservice.dto.SuccessResDTO;
import com.fzcode.fileservice.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ZeroCopyHttpOutputMessage;
import org.springframework.http.codec.multipart.Part;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.*;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Base64;
import java.util.Date;


@RestController
@RequestMapping(value = "/upload")
public class UploadController {

    private Config config;

    @Autowired
    public void setConfig(Config config) {
        this.config = config;
    }

    @GetMapping(value = "/getFile")
    public Mono<String> getFile() {

        return Mono.just(config.getFilePath());
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

    @GetMapping(value = "/test")
    public Mono<Void> downloadByWriteWith(ServerHttpResponse response) throws IOException {
        ZeroCopyHttpOutputMessage zeroCopyResponse = (ZeroCopyHttpOutputMessage) response;
        response.getHeaders().set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=parallel.png");
        response.getHeaders().setContentType(MediaType.IMAGE_PNG);

        Resource resource = new ClassPathResource("parallel.png");
        File file = resource.getFile();
        return zeroCopyResponse.writeWith(file, 0, file.length());
    }

    @PostMapping(value = "/form", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<SuccessResDTO> upload(ServerWebExchange serverWebExchange) {
        Mono<MultiValueMap<String, Part>> multipartData = serverWebExchange.getMultipartData();
        return multipartData.flatMap(multiValueMap -> {
            System.out.println(multiValueMap);
            Part part = multiValueMap.getFirst("file");
            String fileName = part.headers().getContentDisposition().getFilename();
            String preFix = FileUtil.getFilePrefix(fileName);
            String suffix = FileUtil.getFileSuffix(fileName);
            Path tempFile = null;
            String timeStr = String.valueOf(new Date().getTime());
            try {
                tempFile = Files.createTempFile(Paths.get(config.getFilePath()), preFix + timeStr, "." + suffix);
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
            final Path currentTempFile = tempFile;
            String filePath = currentTempFile.toString();
            String[] nameArray = filePath.split("/");
            String fileResName = nameArray[nameArray.length - 1];
            String fileResPath = config.getNetworkFilePath() + "/" + fileResName;
            return DataBufferUtils.write(part.content(), currentChannel, 0).doOnComplete(() -> {
                System.out.println("结束");
            }).then(Mono.just(new SuccessResDTO("上传成功", fileResPath)));

        });
    }

    @PostMapping(value = "/base64", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<String> base64Upload(@RequestBody Base64DTO base64DTO) throws IOException {
        return Mono.create(stringMonoSink -> {
            byte[] bytes = Base64.getDecoder().decode(base64DTO.getBase64());
//        bytes.
            File file = new File(config.getFilePath() + File.separator + base64DTO.getFileName());
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            try {
                bufferedOutputStream.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                bufferedOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            stringMonoSink.success("321");
        });

    }


}