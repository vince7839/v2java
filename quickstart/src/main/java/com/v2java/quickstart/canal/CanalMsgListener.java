package com.v2java.quickstart.canal;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author liaowenxing 2023/7/11
 **/
@Component
@RocketMQMessageListener(topic = "TopicTest",consumerGroup = "group1")
@Slf4j
public class CanalMsgListener implements RocketMQListener<CanalMessage> {


    @Override
    public void onMessage(CanalMessage canalMessage) {
        log.info("canal msg:{}",canalMessage);
    }
}
