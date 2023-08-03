package com.v2java.fs.router.processor;

import com.v2java.fs.MessageProcessor;
import com.v2java.fs.MessageType;
import com.v2java.fs.router.WorkerMessage;
import com.v2java.fs.router.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author liaowenxing 2023/8/2
 **/
@Component
public class WorkerHeartbeatProcessor implements MessageProcessor<WorkerMessage> {

    @Autowired
    WorkerService workerService;

    @Override
    public String getType() {
        return MessageType.WORKER_HEARTBEAT.getCode();
    }

    @Override
    public void process(WorkerMessage workerMessage) {
        workerService.updateHeartbeat(workerMessage);
    }
}
