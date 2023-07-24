package com.v2java.dispatcher;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author liaowenxing 2023/7/11
 **/
@SpringBootApplication
@EnableScheduling
@ComponentScan("com.v2java")
@MapperScan(basePackages = {"com.v2java.dispatcher.dao.mapper"})
public class TaskDispatcherApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskDispatcherApplication.class);
    }
}
