package com.v2java.dispatcher.rpa;

import com.alibaba.fastjson.JSON;
import com.v2java.dispatcher.dto.RpaCallbackDTO;
import com.v2java.dispatcher.dto.RpaHeartbeatDTO;
import com.v2java.dispatcher.mock.MockRpa;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class RpaService {

    public static final String KEY_RPA_SET = "rpa:set";

    @Autowired
    StringRedisTemplate redisTemplate;

    public void heartbeat(RpaHeartbeatDTO heartbeatDTO) {
        redisTemplate.opsForSet().add(KEY_RPA_SET, heartbeatDTO.getRpaId());
        String rpaInfoKey = "rpa:info:" + heartbeatDTO.getRpaId();
        String info = JSON.toJSONString(heartbeatDTO);
        redisTemplate.opsForValue().set(rpaInfoKey, info, 5, TimeUnit.SECONDS);
    }

    public void callback(RpaCallbackDTO callbackDTO) {

    }

    public Rpa dispatchRpa() {
        boolean valid = false;
        Rpa rpa = null;
        //最多重试5次，否则阻塞太久
        int retry = 0;
        do {
            retry ++;
            String rpaId = redisTemplate.opsForSet().pop(KEY_RPA_SET);
            if (StringUtils.isEmpty(rpaId)) {
                return null;
            }
            String rpaInfoKey = "rpa:info:" + rpaId;
            String rpaInfo = redisTemplate.opsForValue().get(rpaInfoKey);
            valid = !StringUtils.isEmpty(rpaInfo);
            if (valid) {
                RpaHeartbeatDTO heartbeatDTO = JSON.parseObject(rpaInfo,RpaHeartbeatDTO.class);
                rpa = new Rpa(heartbeatDTO.getRpaId());
            }
        } while (!valid && retry < 5);
        return rpa;
    }

    public void remove(String rpaId){
        redisTemplate.opsForSet().remove(KEY_RPA_SET,rpaId);
    }
}
