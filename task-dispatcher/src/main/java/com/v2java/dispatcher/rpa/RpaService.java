package com.v2java.dispatcher.rpa;

import com.v2java.dispatcher.dto.RpaCallbackDTO;
import com.v2java.dispatcher.dto.RpaHeartbeatDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RpaService {

    public static final String KEY_RPA_SET = "rpa:set";

    @Autowired
    StringRedisTemplate redisTemplate;

    public void heartbeat(RpaHeartbeatDTO heartbeatDTO){
        redisTemplate.opsForSet().add(KEY_RPA_SET,heartbeatDTO.getRpaId());
    }

    public void callback(RpaCallbackDTO callbackDTO){

    }
}
