package com.v2java.dispatcher;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author liaowenxing 2023/7/13
 **/
@Service
@Slf4j
public class TaskService {

    @Autowired
    @Qualifier("clientSender")
    IMessageSender messageSender;

    String commitTask(TaskRequest request){
        //先分配一个流水
        String flowNum = UUID.randomUUID().toString().replace("-","");
        request.setFlowNum(flowNum);
        messageSender.sendToMq(request.getFlowNum());
        log.info("commitTask:{}",request.getFlowNum());
        return request.getFlowNum();
    }
}
