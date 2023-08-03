package com.v2java.fs;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author liaowenxing 2023/8/1
 **/
@Getter
@AllArgsConstructor
public enum MessageType {
    MASTER_FILE_ACK("WORKER_FILE_ACK","master文件接收反馈"),
    //no need
    SLAVE_FILE_ACK("101","slave文件接收反馈"),
    ROUTER_NOTIFY_SYNC("ROUTER_NOTIFY_SYNC","router通知slave同步"),
    WORKER_HEARTBEAT("WORKER_HEARTBEAT","worker心跳消息"),
    ;
    private String code;
    private String description;
}
