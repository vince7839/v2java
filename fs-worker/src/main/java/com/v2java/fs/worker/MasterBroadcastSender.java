package com.v2java.fs.worker;

import com.alibaba.fastjson.JSON;
import com.v2java.fs.MessageType;
import com.v2java.fs.router.WorkerHeartbeatExtra;
import com.v2java.fs.router.WorkerMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author liaowenxing 2023/8/2
 **/
@Component
@ConditionalOnProperty(prefix = "worker",name = "role",havingValue = "master")
@Slf4j
public class MasterBroadcastSender {

    @Autowired
    RocketMQTemplate mqTemplate;

    @Autowired
    WorkerConfig workerConfig;

    @Autowired
    FileManager bitmapManager;

    @Scheduled(fixedRate = 10 * 1000)
    public void heartbeat() {
        WorkerMessage workerMessage = new WorkerMessage();
        workerMessage.setType(MessageType.MASTER_BROADCAST.getCode());
        BeanUtils.copyProperties(workerConfig, workerMessage);
        WorkerHeartbeatExtra extra = new WorkerHeartbeatExtra();
        extra.setWatermarkBitMap(bitmapManager.toBase64());
        workerMessage.setExtra(JSON.toJSONString(extra));
        mqTemplate.syncSend("TopicWorker", workerMessage);
        log.info("master send broadcast:{}",workerMessage);
    }
}
