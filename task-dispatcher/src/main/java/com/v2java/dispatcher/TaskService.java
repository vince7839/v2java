package com.v2java.dispatcher;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liaowenxing 2023/7/13
 **/
@Service
@Slf4j
public class TaskService {



    String commitTask(TaskRequest request){
       // mqTemplate.convertAndSend("topic-task",request.getFlowNum());
        log.info("commitTask:{}",request.getFlowNum());
        return request.getFlowNum();
    }
}
