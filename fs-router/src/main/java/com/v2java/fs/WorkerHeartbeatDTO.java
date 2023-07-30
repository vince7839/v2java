package com.v2java.fs;

import lombok.Data;

@Data
public class WorkerHeartbeatDTO {

    private String workerId;

    private String group;
    /**
     * master or slave
     */
    private String role;

    private Long watermark;

    private String status;

}
