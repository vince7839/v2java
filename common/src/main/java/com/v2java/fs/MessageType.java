package com.v2java.fs;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author liaowenxing 2023/8/1
 **/
@Getter
@AllArgsConstructor
public enum MessageType {
    UPLOAD_ACK("001","接收完成"),
    ;
    private String code;
    private String description;
}
