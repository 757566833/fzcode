package com.fzcode.internalcommon.dto.servicefile.request;
import org.springframework.web.multipart.MultipartFile;
import lombok.Data;

@Data
public class SingleUpload {
    private MultipartFile file;
    private String filename;
}
