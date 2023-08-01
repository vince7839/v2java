package com.v2java.fs.router.processor;

import com.alibaba.fastjson.JSON;
import com.v2java.dao.mapper.WatermarkFileMapper;
import com.v2java.fs.MessageEnum;
import com.v2java.fs.MessageProcessor;
import com.v2java.fs.MessageType;
import com.v2java.fs.RouterMessage;
import com.v2java.fs.router.FileAckExtra;
import com.v2java.fs.router.FileStatusEnum;
import com.v2java.fs.router.WorkerMessage;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.BitSet;

/**
 * @author liaowenxing 2023/8/1
 **/
@Component
public class MasterFileAckProcessor implements MessageProcessor<WorkerMessage> {

    @Autowired
    WatermarkFileMapper fileMapper;
    @Autowired
    RocketMQTemplate mqTemplate;

    @Override
    public String getType() {
        return MessageType.MASTER_FILE_ACK.getCode();
    }

    @Override
    public void process(WorkerMessage workerMessage) {
        FileAckExtra extra = JSON.parseObject(workerMessage.getExtra(), FileAckExtra.class);
        if (MessageEnum.FILE_ACK_SUCCESS.codeEquals(extra.getResult())){
            fileMapper.updateFileStatus(workerMessage.getGroupId(), extra.getWatermark()
                    , FileStatusEnum.ACCEPT.getCode());
            //即使发送失败也不要紧，worker会定时上报文件信息
            MessageHeaders messageHeaders = new MessageHeaders(null);
            messageHeaders.put("tag",workerMessage.getGroupId());
            RouterMessage routerMessage = new RouterMessage();
            routerMessage.setType(MessageType.ROUTER_NOTIFY_SYNC.getCode());
            Message msg = MessageBuilder.createMessage(routerMessage,messageHeaders);
            mqTemplate.syncSend("GroupWorker",msg);
        }else{
            fileMapper.updateFileStatus(workerMessage.getGroupId(), extra.getWatermark()
                    , FileStatusEnum.FAIL.getCode());
        }

    }
}
