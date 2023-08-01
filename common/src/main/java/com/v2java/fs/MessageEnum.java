package com.v2java.fs;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageEnum {
    FILE_ACK_SUCCESS("0","接收成功"),
    FILE_ACK_FAIL("1",""),
    ;
    private String code;
    private String description;

    public boolean codeEquals(String code){
        return this.getCode().equals(code);
    }
}
