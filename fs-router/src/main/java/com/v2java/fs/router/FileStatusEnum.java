package com.v2java.fs.router;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FileStatusEnum {
    WAIT("0","等待"),
    ACCEPT("1","已上传"),
    SYNC("2","已同步"),
    FAIL("3","失败"),
    ;
    private String code;
    private String description;
}
