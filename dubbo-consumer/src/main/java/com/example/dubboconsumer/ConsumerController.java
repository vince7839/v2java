package com.example.dubboconsumer;

import com.v2java.dto.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ConsumerController {

    @Autowired
    private ConsumerProcessor processor;

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
}
