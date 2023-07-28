package com.v2java.dispatcher.rpa;

import com.v2java.dispatcher.dto.RpaCallbackDTO;
import com.v2java.dispatcher.dto.RpaHeartbeatDTO;
import com.v2java.dispatcher.mock.ResourcePool;
import com.v2java.dispatcher.task.SlidingWindowCounter;
import com.v2java.dto.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class RpaController {

    @Autowired
    RpaService rpaService;

    @Autowired
    ResourcePool resourcePool;

    @Autowired
    RedisTemplate redisTemplate;

    @PostMapping("/rpa/heartbeat")
    public Response heartbeat(@RequestBody RpaHeartbeatDTO heartbeatDTO){
        rpaService.heartbeat(heartbeatDTO);
        return Response.success();
    }

    @PostMapping("/rpa/callback")
    public Response callback(@RequestBody RpaCallbackDTO callbackDTO){
        rpaService.callback(callbackDTO);
        return Response.success();
    }

    @GetMapping("/rpa/list")
    public Response list(){
        return Response.success(resourcePool.getRpaMap());
    }

    @GetMapping("/rpa/ratio")
    public Response ratio(String area){
        Object value = redisTemplate.opsForValue()
                .get(SlidingWindowCounter.KEY_FAIL_RATIO_PREFIX+area);
        return Response.success(value);
    }
}
