package com.v2java.dispatcher.mock;

import lombok.SneakyThrows;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;

public class MockRpa implements Runnable {


    /**
     * 状态空闲
     */
    public final static int STATUS_IDLE = 0;
    /**
     * 状态繁忙
     */
    public final static int STATUS_BUSY = 1;

    int status = 0;

    LocalDateTime latestHeartbeat = LocalDateTime.now();

    LocalDateTime taskStartTime = null;

    int taskUseSeconds = 0;

    @Override
    @SneakyThrows
    public void run() {
        Duration duration = Duration.between(latestHeartbeat, LocalDateTime.now());
        if (duration.getSeconds() > 5) {
            heartbeat();
        }
        if (status == STATUS_BUSY) {
            Duration taskDuration = Duration.between(taskStartTime, LocalDateTime.now());
            if (taskDuration.getSeconds() >= taskUseSeconds) {
                callback();
            }
        }
    }

    public void heartbeat() {
        new Crashable("block") {
            @Override
            void doSth() {
                latestHeartbeat = LocalDateTime.now();
            }
        }.happen();
    }

    public void dispatch() {
        //模拟执行任务，分配时已经决定每个任务耗时
        taskUseSeconds = new Random().nextInt(20);
        taskStartTime = LocalDateTime.now();
        status = STATUS_BUSY;
    }

    public void callback() {
        new Crashable("crash"){
            @Override
            void doSth() {
                status = STATUS_IDLE;
                taskStartTime = null;
            }
        }.happen();
    }
}
