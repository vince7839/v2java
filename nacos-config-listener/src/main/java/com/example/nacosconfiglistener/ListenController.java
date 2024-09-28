package com.example.nacosconfiglistener;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class ListenController {

    @Value("${configKey:ddd}")
    private String config;

    @GetMapping("/v2java/listen/config")
    public ResponseEntity test(){
        return ResponseEntity.ok(config);
    }

}
