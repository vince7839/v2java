package com.v2java.dispatcher.mock;

import com.v2java.dispatcher.dto.RpaHeartbeatDTO;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
public class MockRpa implements Runnable {

    @NonNull
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

    @Autowired
    RestTemplate restTemplate;

    @Override
    @SneakyThrows
    public void run() {
        Duration duration = Duration.between(latestHeartbeat, LocalDateTime.now());
        if (duration.getSeconds() > 5) {
            heartbeat();
        }
        if (status == STATUS_BUSY) {
            Duration taskDuration = Duration.between(taskStartTime, LocalDateTime.now());
            if (taskDuration.getSeconds() >= taskUseSeconds) {
                callback();
            }
        }
    }

    public void heartbeat() {
        RpaHeartbeatDTO heartbeatDTO = new RpaHeartbeatDTO();
        heartbeatDTO.setRpaId(rpaId);
        heartbeatDTO.setStatus(status+"");
        restTemplate.postForObject("http://localhost:8989/rpa/heartbeat",heartbeatDTO,null);
        latestHeartbeat = LocalDateTime.now();
    }

    public void accept(Long taskId) {
        //模拟执行任务，分配时已经决定每个任务耗时
        taskUseSeconds = new Random().nextInt(20);
        taskStartTime = LocalDateTime.now();
        status = STATUS_BUSY;
        this.taskId = taskId;
    }

    public void callback() {
        new Crashable("crash") {
            @Override
            void doSth() {
                status = STATUS_IDLE;
                taskStartTime = null;
                taskId = null;
            }
        }.happen();
    }
}
