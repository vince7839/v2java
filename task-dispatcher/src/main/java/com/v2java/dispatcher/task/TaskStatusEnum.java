package com.v2java.dispatcher.task;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author liaowenxing 2023/7/24
 **/
@AllArgsConstructor
@Getter
public enum TaskStatusEnum {
    STATUS_COMMIT("0","已经提交"),
    STATUS_SUCCESS("1","执行成功"),
    STATUS_PROCESS_ERROR("2","处理失败"),
    STATUS_DISPATCH_FAIL("3","分配失败"),
    STATUS_EXEC_FAIL("4","执行失败"),
    ;
    private String code;
    private String msg;
}
