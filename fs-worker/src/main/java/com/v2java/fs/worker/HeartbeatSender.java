package com.v2java.fs.worker;

import com.v2java.fs.MqMsgType;
import com.v2java.fs.router.WorkerMessage;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author liaowenxing 2023/8/2
 **/
@Component
public class HeartbeatSender {

    @Autowired
    RocketMQTemplate mqTemplate;

    @Autowired
    WorkerConfig workerConfig;

    @Autowired
    FileManager bitmapManager;

    @Scheduled(fixedRate = 10 * 1000)
    public void heartbeat() {
        WorkerMessage workerMessage = new WorkerMessage();
        workerMessage.setType(MqMsgType.WORKER_HEARTBEAT.getCode());
        BeanUtils.copyProperties(workerConfig, workerMessage);
        workerMessage.setWatermarkBitMap(bitmapManager.toBase64());
        workerMessage.setTimestamp(System.currentTimeMillis());
        mqTemplate.syncSend("TopicRouter", workerMessage);
    }
}
