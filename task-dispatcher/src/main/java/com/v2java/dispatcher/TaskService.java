package com.v2java.dispatcher;

import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;

/**
 * @author liaowenxing 2023/7/13
 **/
@Service
@Slf4j
public class TaskService {

    @Autowired
    RocketMQTemplate mqTemplate;

    String commitTask(TaskRequest request){
        mqTemplate.convertAndSend("topic-task",request.getFlowNum());
        log.info("commitTask:{}",request.getFlowNum());
        return request.getFlowNum();
    }
}
