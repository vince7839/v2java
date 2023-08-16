package com.v2java.fs.router;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 * @author liaowenxing 2023/7/31
 **/
@Data
public class GroupState {

    private String groupId;

    private WorkerState master;

    private List<WorkerState> slaveList = new ArrayList<>();
}
