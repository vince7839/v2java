package com.v2java.fs.router;

import java.util.Objects;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class WorkerService {

    Map<String, WorkerMessage> heartbeatCache = new HashMap<>();

    Map<String,GroupState> groupTable = new HashMap<>();

    public void updateHeartbeat(WorkerMessage workerMessage){
        heartbeatCache.put(workerMessage.getWorkerId(),workerMessage);
    }

    @Scheduled(fixedRate = 10*1000)
    public void refresh(){
        heartbeatCache.entrySet().forEach( entry -> {
            String host = entry.getValue().getHost();
            String groupId = entry.getValue().getWorkerId();
            GroupState groupState = groupTable.get(groupId);
            if (Objects.isNull(groupState)){
                groupState = new GroupState();
                groupTable.put(groupId,groupState);
            }

            String workerId = entry.getKey();
            WorkerState workerState = groupState.getWorkerMap().get(workerId);
            if (!workerState.getHost().equals(host)){
                //不同worker配置了相同workerId
                log.warn("worker id conflict");
                return;
            }
            groupState.getWorkerMap().put(workerId,workerState);
        });
    }

    public StorageTarget requestStorage(StorageRequest storageRequest){
        //TODO 对group做负载均衡
        WorkerState workerState = loadBalance(groupTable);
        if (Objects.isNull(workerState)){
            return null;
        }
        StorageTarget storageTarget = new StorageTarget();
        storageTarget.setHost(workerState.getHost());
        storageTarget.setWorkerId(workerState.getWorkerId());
        //分配group内水位

        return storageTarget;
    }

    public WorkerState loadBalance(Map<String,GroupState> groupMap){
        Optional<GroupState> optional = groupMap.values().stream().findAny();
        if (!optional.isPresent()){
            return null;
        }
        Optional<WorkerState> workerStateOptional = optional.get().getWorkerMap()
                .values().stream().findAny();
        if (!workerStateOptional.isPresent()){
            return null;
        }
        return workerStateOptional.get();
    }
}
