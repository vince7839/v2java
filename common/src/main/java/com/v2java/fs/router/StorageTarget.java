package com.v2java.fs.router;

import lombok.Data;

@Data
public class StorageTarget {

    private String workerId;

    private String groupId;

    private String uploadUrl;

    private Long watermark;
}
