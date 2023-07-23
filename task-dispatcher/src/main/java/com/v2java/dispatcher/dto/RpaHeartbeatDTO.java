package com.v2java.dispatcher.dto;

import lombok.Data;

@Data
public class RpaHeartbeatDTO {

    private String rpaId;

    private String ip;

    private String status;
}
