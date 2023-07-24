package com.v2java.dispatcher.task;

import com.v2java.dispatcher.TaskRequest;
import com.v2java.dto.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liaowenxing 2023/7/13
 **/
@RestController
@RequestMapping("task")
public class TaskController {

    @Autowired
    TaskService taskService;

    @PostMapping("commit")
    public Response commit(@RequestBody TaskRequest request){
        String flowNum = taskService.commitTask(request);
        return Response.success(flowNum);
    }
}
