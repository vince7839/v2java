package com.v2java.fs.worker;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "worker")
@Data
public class WorkerConfig {

    private String workerId;

    private String role;

    private String groupId;

    private String dataDir;
}
