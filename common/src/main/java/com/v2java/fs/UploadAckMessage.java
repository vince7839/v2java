package com.v2java.fs;

import lombok.Data;

@Data
public class UploadAckMessage {

    private String workerId;

    private String group;

    private Long watermark;

    private String result;


}
