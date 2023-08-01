package com.v2java.fs.worker;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.v2java.fs.router.StorageTarget;
import com.v2java.fs.router.FileAckExtra;
import lombok.SneakyThrows;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.Objects;

@Service
public class StorageService {

    @Autowired
    WorkerConfig workerConfig;

    @Autowired
    RocketMQTemplate mqTemplate;

    private String SECRET = "123345jgbnjfmvkgmv";

    public void upload(UploadRequest request) {
        DecodedJWT jwt = JWT.require(Algorithm.HMAC256(SECRET))
                            .build()
                            .verify(request.getToken());
        Date expiresAt = jwt.getExpiresAt();
        if (Objects.isNull(expiresAt)||expiresAt.after(new Date())){
            send();
            throw new RuntimeException("jwt过期");
        }
        String json = jwt.getPayload();
        StorageTarget storageTarget = JSON.parseObject(json, StorageTarget.class);
        if (!workerConfig.getWorkerId().equals(storageTarget.getWorkerId())){
            send();
            throw new RuntimeException();
        }
        save(storageTarget,request.getFile());
    }

    @SneakyThrows
    private void save(StorageTarget storageTarget,MultipartFile file){
        //TODO 要不要使用.tmp来保证文件完整性
        String filePath = workerConfig.getDataDir();
        if (!filePath.endsWith("/")){
            filePath = filePath + "/";
        }
        filePath = filePath + storageTarget.getWatermark();
        File localFile = new File(filePath);
        file.transferTo(localFile);
        FileAckExtra ack = new FileAckExtra();
        MessageHeaders headers = new MessageHeaders(null);
        headers.put("tag","UploadAck");
        Message message = MessageBuilder.createMessage(ack,headers);
        mqTemplate.syncSend("TopicRouter",message);
    }

    public void send(){

    }
}
