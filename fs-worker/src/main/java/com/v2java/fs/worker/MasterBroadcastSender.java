package com.v2java.fs.worker;

import com.v2java.fs.MessageType;
import com.v2java.fs.router.WorkerMessage;
import com.v2java.fs.worker.netty.master.WokerNettyServer;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    FileManager fileManager;

    @Autowired
    WokerNettyServer nettyServer;

    final static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Scheduled(fixedRate = 10 * 1000)
    public void heartbeat() {
        WorkerMessage workerMessage = new WorkerMessage();
        workerMessage.setType(MessageType.MASTER_BROADCAST.getCode());
        workerMessage.setWatermarkBitMap(fileManager.toBase64());
        workerMessage.setSyncIp(workerConfig.getIp());
        workerMessage.setSyncPort(nettyServer.getSyncPort());
        workerMessage.setTimestamp(format.format(new Date()));
        BeanUtils.copyProperties(workerConfig, workerMessage);
        mqTemplate.syncSend("TopicWorker", workerMessage);
        log.info("master send broadcast:{}",workerMessage);
    }
}
