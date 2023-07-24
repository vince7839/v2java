package com.v2java.dispatcher.task;

import com.v2java.dispatcher.MqConfig;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@Slf4j
public class TaskLitePullConsumer implements Runnable {

    @Autowired
    MqConfig mqConfig;

    @Autowired
    TaskService taskService;

    ExecutorService executorService = Executors.newSingleThreadExecutor();

    DefaultLitePullConsumer litePullConsumer;

    @PostConstruct
    @SneakyThrows
    public void init() {
        litePullConsumer = new DefaultLitePullConsumer("consuemer-group-test");
        litePullConsumer.setNamesrvAddr(mqConfig.getHost());
        litePullConsumer.subscribe("TopicTest", "*");
        litePullConsumer.setPullBatchSize(100);
        litePullConsumer.setAutoCommit(false);
        litePullConsumer.start();
        executorService.execute(this);
    }


    @Override
    public void run() {
        //这里如果抛异常会有问题
        while (true) {
            List<MessageExt> list = litePullConsumer.poll(1000);
            if (CollectionUtils.isEmpty(list)){
                continue;
            }
            //log.info("lite pull msg size:{}", list.size());
            taskService.process(list);
            litePullConsumer.commitSync();
        }
    }
}
