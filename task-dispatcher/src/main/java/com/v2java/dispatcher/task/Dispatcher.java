package com.v2java.dispatcher.task;

import com.v2java.dispatcher.dao.TaskPO;
import com.v2java.dispatcher.dao.mapper.TaskMapper;
import com.v2java.dispatcher.rpa.Rpa;
import com.v2java.dispatcher.rpa.RpaService;
import com.v2java.util.RedisLockUtil;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author liaowenxing 2023/7/24
 **/
@Component
@Slf4j
public class Dispatcher {

    @Autowired
    RpaService rpaService;
    @Autowired
    TaskMapper taskMapper;
    @Autowired
    RedisLockUtil redisLockUtil;

    public static final String TASK_LOCK_PREFIX = "task:lock:";

    public static final String RPA_LOCK_PREFIX = "rpa:lock:";

    @SneakyThrows
    public void dispatch(TaskPO taskPO) {
        //抢占任务的互斥锁，实现特殊任务串行
        String mutex = getMutex(taskPO);
        if (Objects.nonNull(mutex)) {
            String taskLockKey = TASK_LOCK_PREFIX + mutex;
            //任务锁默认20分钟未执行完则自动释放
            boolean taskLock = redisLockUtil.tryLock(taskLockKey, 20, TimeUnit.MINUTES);
            if (!taskLock) {
                log.info("任务未获取到锁");
                //保存到wait_task
                return;
            }
        }

        Rpa rpa = rpaService.dispatchRpa();
        //没有分配到RPA，记录为失败
        if (Objects.isNull(rpa)) {
            taskMapper.updateStatus(taskPO.getId()
                    , TaskStatusEnum.STATUS_DISPATCH_FAIL.getCode()
                    , "未分配到RPA");
            unlock(mutex, null);
            return;
        }

        //随机产生一个0～1000毫秒的延迟，让后启动的线程有机会先执行，制造并发分配的场景
        Thread.sleep(new Random().nextInt(1000));

        //抢占RPA分配锁，防止在还未分配成功时，rpa心跳又将自己放入待分配列表
        String rpaLockKey = RPA_LOCK_PREFIX + rpa.getRpaId();
        boolean rpaLock = redisLockUtil.tryLock(rpaLockKey, 5, TimeUnit.SECONDS);
        if (!rpaLock) {
            //未获取到RPA锁说明发生了并发分配，则需要把任务锁释放掉，消费失败
            unlock(null, rpa.getRpaId());
            return;
        }
        boolean success = false;
        try {
            success = rpa.sendTask(taskPO);
            taskMapper.updateStatus(taskPO.getId(),TaskStatusEnum.STATUS_DISPATCH_SUCCESS.getCode()
                    ,"下发到RPA-"+rpa.getRpaId());
        } catch (Exception e) {
            log.error("下发任务失败", e);
        } finally {
            //未成功下发，需要解锁（会不会释放其他线程的锁）
            if (!success) {
                unlock(mutex, rpa.getRpaId());
            }else {
                //成功了再删除一次该rpa，避免在分配期间又放入
            }
        }
    }

    public void unlock(String mutex, String rpaId) {
        if (!StringUtils.isEmpty(mutex)) {
            redisLockUtil.unlock("task:lock:" + mutex);
        }
        if (!StringUtils.isEmpty(rpaId)) {
            redisLockUtil.unlock("rpa:lock:" + rpaId);
        }
    }

    public String getMutex(TaskPO taskPO) {
        //模拟每20个任务出现一次冲突
        if (taskPO.getId() % 20 == 1) {
            return "mutex_test";
        }
        return null;
    }
}
