package com.v2java.fs.router.processor;

import com.alibaba.fastjson.JSON;
import com.v2java.dao.mapper.WatermarkFileMapper;
import com.v2java.fs.MessageEnum;
import com.v2java.fs.MessageProcessor;
import com.v2java.fs.MessageType;
import com.v2java.fs.router.FileAckExtra;
import com.v2java.fs.router.FileStatusEnum;
import com.v2java.fs.router.WorkerMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Deprecated
public class SlaveFileAckProcessor implements MessageProcessor<WorkerMessage> {

    @Autowired
    WatermarkFileMapper fileMapper;

    @Override
    public String getType() {
        return MessageType.SLAVE_FILE_ACK.getCode();
    }

    @Override
    public void process(WorkerMessage workerMessage) {
        FileAckExtra extra = JSON.parseObject(workerMessage.getExtra(), FileAckExtra.class);
        String status = MessageEnum.FILE_ACK_SUCCESS.getCode().equals(extra.getResult()) ?
                FileStatusEnum.SYNC.getCode() : FileStatusEnum.FAIL.getCode();
        fileMapper.updateFileStatus(workerMessage.getGroupId(), extra.getWatermark(), status);
    }
}
