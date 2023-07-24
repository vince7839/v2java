package com.v2java.util;

import com.v2java.RedisConfig;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author liaowenxing 2023/7/24
 **/
@Component
@Slf4j
public class RedisLockUtil {

    @Autowired
    RedisConfig redisConfig;

    @Autowired
    RedissonClient redissonClient;

    public boolean tryLock(String key,int time,TimeUnit timeUnit){
        try {
            RLock lock = redissonClient.getLock(key);
            boolean isLock = lock.tryLock(1, time,timeUnit);
            return isLock;
        }catch (Exception e){
            log.error("redis lock error",e);
        }
        return false;
    }

    public void unlock(String key){
        RLock lock = redissonClient.getLock(key);
        lock.unlock();
    }

    @Bean
    public RedissonClient redissonClient(){
        Config config = new Config();
        SingleServerConfig singleServerConfig = config.useSingleServer();
        if (!StringUtils.isEmpty(redisConfig.getPassword())) {
            singleServerConfig.setPassword(redisConfig.getPassword());
        }
        singleServerConfig.setAddress(
                "redis://" + redisConfig.getHost() + ":" + redisConfig.getPort());
        singleServerConfig.setDatabase(redisConfig.getDatabase());
        return Redisson.create(config);
    }
}
