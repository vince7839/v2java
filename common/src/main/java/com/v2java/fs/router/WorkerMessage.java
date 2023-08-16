package com.v2java.fs.router;

import lombok.Data;

/**
 * @author liaowenxing 2023/8/1
 **/
@Data
public class WorkerMessage {

    private String type;

    private String host;

    private String workerId;

    private String groupId;

    private String role;

    private String watermarkBitMap;

    private String extra;

    private String syncIp;

    private Integer syncPort;

    private Long timestamp;

}
