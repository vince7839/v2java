package com.v2java.dispatcher;

import com.v2java.dispatcher.dto.RpaCallbackDTO;
import com.v2java.dispatcher.dto.RpaHeartbeatDTO;
import com.v2java.dispatcher.rpa.RpaService;
import com.v2java.dto.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class RpaController {

    @Autowired
    RpaService rpaService;

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
}
