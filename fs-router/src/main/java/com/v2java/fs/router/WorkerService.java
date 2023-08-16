package com.v2java.fs.router;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WorkerService {

    Map<String, WorkerMessage> heartbeatCache = new HashMap<>();

    Map<String, GroupState> groupTable = new HashMap<>();

    public void updateHeartbeat(WorkerMessage workerMessage) {
        heartbeatCache.put(workerMessage.getWorkerId(), workerMessage);
    }

    @Scheduled(fixedRate = 10 * 1000)
    public void refresh() {
        groupTable.clear();
        long now = System.currentTimeMillis();
        heartbeatCache.entrySet().forEach(entry -> {
            String workerId = entry.getKey();
            WorkerMessage msg = entry.getValue();
            //10s之前的不处理
            if (Objects.isNull(msg.getTimestamp())
                    || now - msg.getTimestamp() > 10 * 1000) {
                return;
            }

            GroupState groupState = getGroup(msg);
            if (Objects.isNull(groupState)){
                log.error("heartbeat no group info:{}",msg);
                return;
            }

            WorkerState workerState = createWorkerState(msg);
            if ("master".equals(workerState.getRole())) {
                groupState.setMaster(workerState);
            } else {
                groupState.getSlaveList().add(workerState);
            }
        });
    }

    public WorkerState createWorkerState(WorkerMessage msg) {
        WorkerState workerState = new WorkerState();
        BeanUtils.copyProperties(msg, workerState);
        return workerState;
    }

    public GroupState getGroup(WorkerMessage msg){
        if (StringUtils.isEmpty(msg.getGroupId())){
            log.error("wrong group id");
            return null;
        }
        GroupState groupState = groupTable.get(msg.getGroupId());
        if (Objects.isNull(groupState)) {
            groupState = new GroupState();
            groupState.setGroupId(msg.getGroupId());
            groupTable.put(msg.getGroupId(), groupState);
        }
        return groupState;
    }


    public StorageTarget requestStorage(StorageRequest storageRequest) {
        //TODO 对group做负载均衡
        WorkerState workerState = loadBalance(groupTable);
        if (Objects.isNull(workerState)) {
            log.error("no master to upload");
            return null;
        }
        StorageTarget storageTarget = new StorageTarget();
        storageTarget.setUploadUrl(workerState.getUploadUrl());
        storageTarget.setWorkerId(workerState.getWorkerId());
        storageTarget.setGroupId(workerState.getGroupId());
        //分配group内水位

        return storageTarget;
    }

    public WorkerState loadBalance(Map<String, GroupState> groupMap) {
        //负载均衡
        for (GroupState groupState : groupMap.values()) {
            if (Objects.isNull(groupState.getMaster())){
                log.warn("group lose master:{}",groupState);
                continue;
            }
            return groupState.getMaster();
        }
        return null;
    }
}
