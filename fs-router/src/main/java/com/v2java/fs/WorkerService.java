package com.v2java.fs;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class WorkerService {

    Map<String,WorkerHeartbeatDTO> workerTable = new HashMap<>();

    public void update(WorkerHeartbeatDTO heartbeatDTO){
        workerTable.put(heartbeatDTO.getWorkerId(),heartbeatDTO);

    }

    @Scheduled(fixedRate = 1000)
    public void refresh(){

    }

    public StorageTarget requestStorage(StorageRequest storageRequest){
        //TODO 对group做负载均衡
        StorageTarget storageTarget = new StorageTarget();
        return storageTarget;
    }
}
