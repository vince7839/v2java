package com.v2java.fs.worker;

import com.v2java.dto.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WorkerController {

    @Autowired
    StorageService storageService;

    @PostMapping("/upload")
    public Response upload(UploadRequest request){
        storageService.upload(request);
        return Response.success();
    }
}
