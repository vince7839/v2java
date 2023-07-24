package com.v2java.dispatcher.rpa;

import com.v2java.dispatcher.dao.TaskPO;
import com.v2java.dispatcher.mock.MockRpa;
import com.v2java.dispatcher.mock.ResourcePool;
import com.v2java.util.SpringUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author liaowenxing 2023/7/24
 **/
@Data
@Slf4j
public class Rpa {

    private String rpaId;

    private MockRpa mockRpa;

    public Rpa(String rpaId){
        mockRpa = SpringUtil.getBean(ResourcePool.class).getMock(rpaId);
    }

    public boolean sendTask(TaskPO taskPO){
        try {
            mockRpa.accept(taskPO.getId());
            return true;
        }catch (Exception e){
            log.error("任务下发至RPA异常",e);
            return false;
        }
    }
}
