package com.v2java.fs.worker.processor;

import com.v2java.fs.MessageProcessor;
import com.v2java.fs.MessageType;
import com.v2java.fs.router.WorkerMessage;
import com.v2java.fs.worker.netty.WorkerNettyClient;
import java.util.Base64;
import java.util.BitSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * @author liaowenxing 2023/8/10
 **/
@Component
@Slf4j
@ConditionalOnProperty(name = "worker.role",havingValue = "slave")
public class MasterBroadcastProcessor implements MessageProcessor<WorkerMessage> {

    @Autowired
    WorkerNettyClient workerNettyClient;

    @Override
    public String getType() {
        return MessageType.MASTER_BROADCAST.getCode();
    }

    @Override
    public void process(WorkerMessage workerMessage) {
        String bitMapStr = workerMessage.getWatermarkBitMap();
        byte[] bytes = Base64.getDecoder().decode(bitMapStr);
        BitSet bitSet = BitSet.valueOf(bytes);
        log.info("recv master bitmap:{}",bitSet.toString());
    }
}
