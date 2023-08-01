package com.v2java.fs.router;

import com.v2java.fs.MessageProcessor;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author liaowenxing 2023/8/1
 **/
@RocketMQMessageListener(consumerGroup = "group-router",topic = "TopicRouter")
@Component
@Slf4j
public class MessageListener implements RocketMQListener<WorkerMessage> {

    @Autowired
    Set<MessageProcessor> processorSet;

    Map<String,MessageProcessor> processorMap = new HashMap<>();

    @PostConstruct
    public void init(){
        if (!CollectionUtils.isEmpty(processorSet)){
            processorSet.forEach(i -> processorMap.put(i.getType(),i));
        }
    }

    @Override
    public void onMessage(WorkerMessage workerMessage) {
        MessageProcessor<WorkerMessage> processor = processorMap.get(workerMessage.getType());
        if (Objects.isNull(processor)){
            log.warn("unknown message:{}",workerMessage);
            return;
        }
        processor.process(workerMessage);
    }
}
