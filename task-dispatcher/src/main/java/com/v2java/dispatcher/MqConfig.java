package com.v2java.dispatcher;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author liaowenxing 2023/7/21
 **/
@Configuration
@ConfigurationProperties(prefix = "mq")
@Data
public class MqConfig {
    private String host;
    private String proxy;
    private String topic;
}
