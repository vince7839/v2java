package com.v2java.dispatcher.mock;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MockInfo {

    private String rpaId;

    private int status;

    private LocalDateTime latestHeartbeat = LocalDateTime.now();

    private LocalDateTime taskStartTime = null;

    private int taskUseSeconds = 0;

    private Long taskId;
}
