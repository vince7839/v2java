package com.v2java.dispatcher.task;

import com.alibaba.fastjson.JSON;
import com.v2java.dispatcher.IMessageSender;
import com.v2java.dispatcher.TaskRequest;
import com.v2java.dispatcher.dao.TaskPO;
import com.v2java.dispatcher.dao.mapper.TaskMapper;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author liaowenxing 2023/7/13
 **/
@Service
@Slf4j
public class TaskService implements RejectedExecutionHandler {

    @Autowired
    @Qualifier("clientSender")
    IMessageSender messageSender;

    @Autowired
    @Qualifier("dispatcherPool")
    ExecutorService executorService;

    @Autowired
    Dispatcher dispatcher;

    @Autowired
    TaskMapper taskMapper;

    String commitTask(TaskRequest request) {
        //先分配一个流水
        String flowNum = UUID.randomUUID().toString().replace("-", "");
        request.setFlowNum(flowNum);
        String msg = JSON.toJSONString(request);
        messageSender.sendToMq(msg);
        log.info("commitTask:{}", msg);
        return request.getFlowNum();
    }

    void process(List<MessageExt> list) {
        //同步过程只需要把task记录插入数据库
        List<TaskPO> taskList = list.stream().map(this::convertTask).collect(Collectors.toList());
        taskMapper.insertBatch(taskList);
        //后续处理放入线程池处理
        for (TaskPO taskPO: taskList) {
            DispatchRunnable dispatchRunable = new DispatchRunnable(taskPO,dispatcher);
            executorService.execute(dispatchRunable);
        }
    }

    @Bean("dispatcherPool")
    public ExecutorService dispatcherPool() {
        ExecutorService executorService = new ThreadPoolExecutor(3, 5, 1
                , TimeUnit.MINUTES, new ArrayBlockingQueue<>(100), new ThreadFactory() {
            int i = 0;

            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("dispatcher-thread-" + i++);
                return thread;
            }
        }, this);
        return executorService;
    }

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        //提交线程池失败，重试次数小于3重新提交，否则更新状态为失败
        if (((DispatchRunnable)r).getRetry() < 3){
            ((DispatchRunnable) r).increRetry();
            executorService.execute(r);
        }else{
            taskMapper.updateStatus(((DispatchRunnable) r).getTask().getId()
                    ,TaskStatusEnum.STATUS_PROCESS_ERROR.getCode(),"线程池繁忙");
        }
    }

    public TaskPO convertTask(MessageExt messageExt){
        String msg = new String(messageExt.getBody());
        Task task = JSON.parseObject(msg,Task.class);
        TaskPO taskPO = new TaskPO();
        taskPO.setFlowNum(task.getFlowNum());
        taskPO.setTaskType(task.getTaskType());
        //0未处理
        taskPO.setStatus("0");
        return taskPO;
    }
}
