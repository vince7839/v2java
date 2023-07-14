package com.v2java.dispatcher;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author liaowenxing 2023/7/14
 **/
@Component
public class TaskConsumer {

    @Autowired
    RocketMQTemplate mqTemplate;

    @Autowired
    @Qualifier("consumerPoll")
    ExecutorService executorService;

    @Scheduled(fixedRate = 1000)
    public void pull(){
        List<String> msgList = mqTemplate.receive(String.class);

    }

    @Bean("consumerPoll")
    public ExecutorService poll(){
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 3, 0,
                TimeUnit.SECONDS, new ArrayBlockingQueue(100), new ThreadFactory() {
            int i = 0;
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread();
                thread.setName("consumer-thread-" + i++);
                return thread;
            }
        }, new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

            }
        });
        return executor;
    }
}
