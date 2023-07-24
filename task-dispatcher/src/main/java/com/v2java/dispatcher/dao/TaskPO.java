package com.v2java.dispatcher.dao;

import lombok.Data;

/**
 * @author liaowenxing 2023/7/24
 **/
@Data
public class TaskPO {
    private Long id;
    private String flowNum;
    private String taskType;
    private String status;
    private String message;
}
