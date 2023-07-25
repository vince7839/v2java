package com.v2java.dispatcher.rpa;

import com.alibaba.fastjson.JSON;
import com.v2java.dispatcher.dto.RpaCallbackDTO;
import com.v2java.dispatcher.dto.RpaHeartbeatDTO;
import com.v2java.dispatcher.mock.MockRpa;
import com.v2java.dispatcher.task.Dispatcher;
import com.v2java.dispatcher.task.TaskService;
import com.v2java.util.RedisLockUtil;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Slf4j
public class RpaService {

    public static final String KEY_RPA_SET = "rpa:set";

    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    RedisLockUtil redisLockUtil;

    public void heartbeat(RpaHeartbeatDTO heartbeatDTO) {
        boolean lock = redisLockUtil.tryLock(Dispatcher.RPA_LOCK_PREFIX + heartbeatDTO.getRpaId()
                , 2, TimeUnit.SECONDS);
        //如果没获取到锁，说明该RPA正在分配中
        if (lock) {
           // redisTemplate.opsForSet().add(KEY_RPA_SET, heartbeatDTO.getRpaId());
        }
        String rpaInfoKey = "rpa:info:" + heartbeatDTO.getRpaId();
        String info = JSON.toJSONString(heartbeatDTO);
       // redisTemplate.opsForValue().set(rpaInfoKey, info, 7, TimeUnit.SECONDS);
    }

    public void callback(RpaCallbackDTO callbackDTO) {
        redisLockUtil.unlock(Dispatcher.RPA_LOCK_PREFIX + callbackDTO.getRpaId());
        if (!StringUtils.isEmpty(callbackDTO.getMutex())) {
            redisLockUtil.unlock(Dispatcher.TASK_LOCK_PREFIX + callbackDTO.getMutex());
        }
        //TODO 统计失败率
    }

    public Rpa dispatchRpa() {
        boolean valid = false;
        Rpa rpa = null;
        //最多重试5次，否则阻塞太久
        int retry = 0;
        do {
            retry++;
            String rpaId = redisTemplate.opsForSet().pop(KEY_RPA_SET);
            if (StringUtils.isEmpty(rpaId)) {
                return null;
            }
            String rpaInfoKey = "rpa:info:" + rpaId;
            String rpaInfo = redisTemplate.opsForValue().get(rpaInfoKey);
            valid = !StringUtils.isEmpty(rpaInfo);
            if (valid) {
                //RpaHeartbeatDTO heartbeatDTO = JSON.parseObject(rpaInfo,RpaHeartbeatDTO.class);
                log.warn("RPA已经离线：{}",rpaId);
                rpa = new Rpa(rpaId);
            }
        } while (!valid && retry < 5);
        return rpa;
    }

    public void remove(String rpaId) {
        redisTemplate.opsForSet().remove(KEY_RPA_SET, rpaId);
    }
}
