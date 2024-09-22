package com.v2java.quickstart;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@Slf4j
public class TestController {

    AtomicInteger count = new AtomicInteger(0);

    @GetMapping("/test/sleep")
    @SneakyThrows
    public String test(){
        log.info("count:"+count.incrementAndGet());
        TimeUnit.HOURS.sleep(1);
        return "ok";
    }

    @GetMapping("/test/add")
    @SneakyThrows
    public String test2(){

        log.info("count:"+count.incrementAndGet());
        return count.toString();
    }
}
