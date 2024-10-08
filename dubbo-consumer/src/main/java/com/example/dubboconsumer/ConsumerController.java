package com.example.dubboconsumer;

import com.alibaba.cloud.nacos.NacosConfigProperties;
import com.v2java.dto.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RefreshScope
public class ConsumerController {

    @Autowired
    private ConsumerProcessor processor;

    @Value("${ppp:defaultV2java}")
    private String config;

    @Autowired
    private NacosConfigProperties properties;

    @GetMapping("/v2java/hello")
    public Response hello(String name){
        log.info("hello");
        return Response.success(processor.hello(name));
    }

    @GetMapping("/v2java/bye")
    public Response bye(String name){
        log.info("bye");
        return Response.success(processor.bye(name));
    }

    @GetMapping("/v2java/config")
    public Response config(){
        log.info("config");
        log.info("nacos config:"+properties.toString());
        return Response.success(config);
    }

    @GetMapping("/v2java/sha")
    public Response sha(){
        log.info("sha");
        return Response.success(System.getenv("COMMIT_ID"));
    }
}
