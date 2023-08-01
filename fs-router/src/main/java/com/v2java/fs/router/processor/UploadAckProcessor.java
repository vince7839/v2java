package com.v2java.fs.router.processor;

import com.v2java.dao.mapper.WatermarkFileMapper;
import com.v2java.fs.MessageProcessor;
import com.v2java.fs.MessageType;
import com.v2java.fs.router.WorkerMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author liaowenxing 2023/8/1
 **/
@Component
public class UploadAckProcessor implements MessageProcessor<WorkerMessage> {

    @Autowired
    WatermarkFileMapper fileMapper;

    @Override
    public String getType() {
        return MessageType.UPLOAD_ACK.getCode();
    }

    @Override
    public void process(WorkerMessage workerMessage) {

    }
}
