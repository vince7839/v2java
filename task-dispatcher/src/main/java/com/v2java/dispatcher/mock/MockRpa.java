package com.v2java.dispatcher.mock;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.v2java.dispatcher.dto.RpaHeartbeatDTO;
import com.v2java.dto.Response;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.ToString;
import org.springframework.context.annotation.Scope;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Data
@Component
@Scope("prototype")
public class MockRpa implements Runnable {

    private String rpaId;
    /**
     * 状态空闲
     */
    public final static int STATUS_IDLE = 0;
    /**
     * 状态繁忙
     */
    public final static int STATUS_BUSY = 1;

    int status = 0;

    LocalDateTime latestHeartbeat = LocalDateTime.now();

    LocalDateTime taskStartTime = null;

    int taskUseSeconds = 0;

    Long taskId;

    @JsonIgnore
    RestTemplate restTemplate = new RestTemplate(new SimpleClientHttpRequestFactory());

    @Override
    @SneakyThrows
    public void run() {
        Duration duration = Duration.between(latestHeartbeat, LocalDateTime.now());
        if (duration.getSeconds() > 5) {
            heartbeat();
        }
        if (status == STATUS_BUSY) {
            Duration taskDuration = Duration.between(taskStartTime, LocalDateTime.now());
            if (taskDuration.getSeconds() > taskUseSeconds) {
                callback();
            }
        }
    }

    @Crashable
    public void heartbeat() {
        RpaHeartbeatDTO heartbeatDTO = new RpaHeartbeatDTO();
        heartbeatDTO.setRpaId(rpaId);
        heartbeatDTO.setStatus(status + "");
        restTemplate
                .postForObject("http://localhost:8989/rpa/heartbeat", heartbeatDTO, Response.class);
        latestHeartbeat = LocalDateTime.now();
    }

    @Crashable(type = "block",maxBlock = 5000)
    public void accept(Long taskId) {
        //模拟执行任务，分配时已经决定每个任务耗时
        taskUseSeconds = new Random().nextInt(10);
        taskStartTime = LocalDateTime.now();
        status = STATUS_BUSY;
        this.taskId = taskId;
    }

    @Crashable
    public void callback() {
        status = STATUS_IDLE;
        taskStartTime = null;
        taskId = null;
    }
}
