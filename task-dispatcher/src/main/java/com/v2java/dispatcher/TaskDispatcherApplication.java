package com.v2java.dispatcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author liaowenxing 2023/7/11
 **/
@SpringBootApplication
@EnableScheduling
public class TaskDispatcherApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskDispatcherApplication.class);
    }
}
