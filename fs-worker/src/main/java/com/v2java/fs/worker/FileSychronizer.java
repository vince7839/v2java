package com.v2java.fs.worker;

import com.alibaba.fastjson.JSONObject;
import com.v2java.NettyMsgType;
import com.v2java.fs.MessageType;
import com.v2java.fs.worker.netty.WorkerNettyClient;
import java.util.BitSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author liaowenxing 2023/8/10
 **/
@Component
public class FileSychronizer {

    @Autowired
    WorkerNettyClient workerNettyClient;

    public void sync(BitSet masterBit,BitSet localBit){
        for (int i=0;i<masterBit.length();i++){
            boolean masterExist = masterBit.get(i);
            boolean localExist = localBit.get(i);
            if (masterExist == localExist){
                continue;
            }
            if (masterExist){
                //master存在，本地不存在
                syncFromMaster(i);
            }else{
                //master不存在，本地存在
            }
        }
    }

    public void syncFromMaster(int watermark){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("watermark",watermark);
        workerNettyClient.send(NettyMsgType.STRING);
    }
}
