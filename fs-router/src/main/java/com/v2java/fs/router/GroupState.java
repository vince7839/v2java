package com.v2java.fs.router;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Data;

/**
 * @author liaowenxing 2023/7/31
 **/
@Data
public class GroupState {

    private String groupId;

    private Map<String,WorkerState> workerMap = new HashMap<>();
}
