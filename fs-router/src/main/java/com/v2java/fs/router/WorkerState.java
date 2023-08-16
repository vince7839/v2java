package com.v2java.fs.router;

import lombok.Data;

/**
 * @author liaowenxing 2023/7/31
 **/
@Data
public class WorkerState {

    private String workerId;

    private String groupId;

    private String role;

    private String uploadUrl;

    private Long totalSpaceMb;

    private Long usedSpaceMb;


}
