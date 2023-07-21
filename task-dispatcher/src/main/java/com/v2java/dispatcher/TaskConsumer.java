package com.v2java.dispatcher;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.apis.ClientConfiguration;
import org.apache.rocketmq.client.apis.ClientServiceProvider;
import org.apache.rocketmq.client.apis.consumer.FilterExpression;
import org.apache.rocketmq.client.apis.consumer.FilterExpressionType;
import org.apache.rocketmq.client.apis.consumer.SimpleConsumer;
import org.apache.rocketmq.client.apis.message.MessageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author liaowenxing 2023/7/14
 **/
@Component
@Slf4j
public class TaskConsumer {

    @Autowired
    @Qualifier("consumerPoll")
    ExecutorService executorService;

    @Autowired
    MqConfig mqConfig;

    @Autowired
    SimpleConsumer simpleConsumer;

    @Scheduled(fixedRate = 1000)
    @SneakyThrows
    public void pull() {
        List<MessageView> list = simpleConsumer.receive(100,Duration.ofSeconds(10));
        log.info("pull msg size：{}",list.size());
    }

    @Bean("consumerPoll")
    public ExecutorService poll() {
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

    @Bean
    @SneakyThrows
    public SimpleConsumer simpleConsumer(){
        // 消费示例：使用 SimpleConsumer 消费普通消息，主动获取消息处理并提交。
        ClientServiceProvider provider = ClientServiceProvider.loadService();
        String topic = "TopicTest";
        String consumerGroup = "group-A";
        FilterExpression filterExpression = new FilterExpression("*", FilterExpressionType.TAG);
        SimpleConsumer simpleConsumer = provider.newSimpleConsumerBuilder()
                // 设置消费者分组。
                .setConsumerGroup(consumerGroup)
                // 设置接入点。
                .setClientConfiguration(ClientConfiguration.newBuilder().setEndpoints(mqConfig.getProxy()).build())
                // 设置预绑定的订阅关系。
                .setSubscriptionExpressions(Collections.singletonMap(topic, filterExpression))
                // 设置从服务端接受消息的最大等待时间
                .setAwaitDuration(Duration.ofSeconds(1))
                .build();
        return simpleConsumer;
    }
}
