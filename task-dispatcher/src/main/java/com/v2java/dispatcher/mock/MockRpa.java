package com.v2java.dispatcher.mock;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.v2java.dispatcher.dao.TaskPO;
import com.v2java.dispatcher.dto.RpaCallbackDTO;
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

    TaskPO currentTask;

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
    public void accept(TaskPO task) {
        //模拟执行任务，分配时已经决定每个任务耗时
        taskUseSeconds = new Random().nextInt(10);
        taskStartTime = LocalDateTime.now();
        status = STATUS_BUSY;
        this.currentTask = task;
    }

    @Crashable
    public void callback() {
        RpaCallbackDTO callbackDTO = new RpaCallbackDTO();
        callbackDTO.setFlowNum(currentTask.getFlowNum());
        //随机生成一个执行结果 0：成功 1：网络原因失败 2：其他失败
        callbackDTO.setResult(new Random().nextInt(3)+"");
        //先随机写死一个地区
        callbackDTO.setArea("110000");
        restTemplate.postForObject("http://localhost:8989/rpa/callback"
                ,callbackDTO,Response.class);
        status = STATUS_IDLE;
        taskStartTime = null;
        currentTask = null;
    }
}
