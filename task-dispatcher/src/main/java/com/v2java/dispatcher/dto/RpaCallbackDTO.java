package com.v2java.dispatcher.dto;

import lombok.Data;

@Data
public class RpaCallbackDTO {

    private String flowNum;

    private String status;

    private String rpaId;

    private String mutex;

    private String result;

    private String area;
}
