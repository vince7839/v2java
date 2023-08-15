package com.v2java;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author liaowenxing 2023/8/10
 **/
@Getter
@AllArgsConstructor
public enum NettyMsgType {
    STRING((byte)0),
    FILE((byte)1),
    SYNC((byte)2),
    ;
    private byte code;
}
