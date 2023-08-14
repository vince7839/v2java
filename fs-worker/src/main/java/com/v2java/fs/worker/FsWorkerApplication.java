package com.v2java.fs.worker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FsWorkerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FsWorkerApplication.class, args);
    }

}
