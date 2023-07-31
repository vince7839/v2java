package com.v2java.fs.router;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class RouteService {

    @Autowired
    WorkerService workerService;

    private String SECRET = "123345jgbnjfmvkgmv";

    public String getToken(StorageRequest storageRequest) {
        StorageTarget storageTarget = workerService.requestStorage(storageRequest);
        long nowMills = System.currentTimeMillis();
        long EXPIRE_MILLS = 60 * 3 * 1000;
        String token = JWT.create()
                .withIssuedAt(new Date(nowMills))
                .withExpiresAt(new Date(nowMills + EXPIRE_MILLS))
                .withPayload(JSON.toJSONString(storageTarget))
                .withIssuer("auth0")
                .sign(Algorithm.HMAC256(SECRET));
        log.info("request:{},token:{}",storageRequest,token);
        return token;
    }
}
