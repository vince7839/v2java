package com.v2java.fs.worker;

import com.v2java.fs.worker.netty.slave.SyncPacket;
import com.v2java.fs.worker.netty.WorkerNettyClient;
import java.io.File;
import java.util.BitSet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author liaowenxing 2023/8/10
 **/
@Component
@Slf4j
public class FileSynchronizer {

    @Autowired
    WorkerNettyClient workerNettyClient;
    @Autowired
    FileManager fileManager;

    public void syncIfNeed(BitSet masterBit) {
        BitSet localBit = fileManager.getBitSet();
        for (int i = 0; i < masterBit.length(); i++) {
            boolean masterExist = masterBit.get(i);
            boolean localExist = localBit.get(i);
            if (masterExist == localExist) {
                continue;
            }
            if (masterExist) {
                //master存在，本地不存在
                syncFromMaster((long) i);
            } else {
                //master不存在，本地存在
                deleteByWatermark(i);
            }
        }
    }

    private void deleteByWatermark(int i) {
        File file = new File(String.valueOf(i));
        if (file.exists()) {
            file.delete();
        }
    }

    public void syncFromMaster(Long watermark) {
        workerNettyClient.send(new SyncPacket(watermark, 0L));
    }
}
