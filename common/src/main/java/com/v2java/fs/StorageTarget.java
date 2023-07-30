package com.v2java.fs;

import lombok.Data;

@Data
public class StorageTarget {

    private String workerId;

    private String host;

    private Long watermark;
}
