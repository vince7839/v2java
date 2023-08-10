package com.v2java;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author liaowenxing 2023/8/10
 **/
@Getter
@AllArgsConstructor
public enum NettyMsgType {
    STRING(0),
    FILE(1),
    ;
    private int code;
}
