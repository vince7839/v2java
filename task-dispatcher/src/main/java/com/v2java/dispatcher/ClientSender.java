package com.v2java.dispatcher;

import lombok.SneakyThrows;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ClientSender implements IMessageSender<String> {

    DefaultMQProducer producer;

    @Autowired
    MqConfig mqConfig;

    @PostConstruct
    @SneakyThrows
    public void init() {
        producer = new DefaultMQProducer("producer-group-test");
        // 设置NameServer地址
        producer.setNamesrvAddr(mqConfig.getHost());
        // 启动producer
        producer.start();
    }

    @Override
    @SneakyThrows
    public void sendToMq(String msg) {
        Message message = new Message(mqConfig.getTopic(), msg.getBytes());
        producer.send(message);
    }
}
