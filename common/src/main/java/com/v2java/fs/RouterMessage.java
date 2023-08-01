package com.v2java.fs;

import lombok.Data;

@Data
public class RouterMessage {

    private String type;

    private String targetGroupId;

    private String watermark;

}
