package com.v2java.fsworker;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadRequest {

    private MultipartFile file;

    private String token;
}
