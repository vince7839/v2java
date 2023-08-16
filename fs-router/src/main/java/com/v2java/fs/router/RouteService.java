package com.v2java.fs.router;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.util.Objects;
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
        if (!auth(storageRequest)){
            throw new RuntimeException("auth error");
        }
        StorageTarget storageTarget = workerService.requestStorage(storageRequest);
        if (Objects.isNull(storageTarget)){
            throw new RuntimeException("service error");
        }
        long nowMills = System.currentTimeMillis();
        long EXPIRE_MILLS = 60 * 30 * 1000;
        String token = JWT.create()
                .withIssuedAt(new Date(nowMills))
                .withExpiresAt(new Date(nowMills + EXPIRE_MILLS))
                .withClaim("cert",JSON.toJSONString(storageTarget))
                .withIssuer("auth0")
                .sign(Algorithm.HMAC256(SECRET));
        log.info("request:{},token:{}",storageRequest,token);
        return token;
    }

    private boolean auth(StorageRequest storageRequest) {
        if (Objects.isNull(storageRequest)){
            return false;
        }
        if (!"123".equals(storageRequest.getUsername())
                ||!"123".equals(storageRequest.getPassword())){
            return false;
        }
        return true;
    }
}
