package com.v2java;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author liaowenxing 2023/7/24
 **/
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.redis")
public class RedisConfig {
    private String host;
    private String port;
    private String password;
    private int database;
}
