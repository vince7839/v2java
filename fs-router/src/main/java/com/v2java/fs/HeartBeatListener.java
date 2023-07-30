package com.v2java.fs;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@RocketMQMessageListener(topic = "TopicRouter",consumerGroup = "GroupRouter")
@Component
public class HeartBeatListener implements RocketMQListener<WorkerHeartbeatDTO> {

    @Autowired
    WorkerService workerService;

    @Override
    public void onMessage(WorkerHeartbeatDTO heartbeatDTO) {
        workerService.update(heartbeatDTO);
    }
}
