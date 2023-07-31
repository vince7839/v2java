package com.v2java.fs.router;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan(basePackages = {"com.v2java.dao.mapper"})
@ComponentScan(basePackages = "com.v2java")
public class FsRouterApplication {

    public static void main(String[] args) {
        SpringApplication.run(FsRouterApplication.class, args);
    }

}
