package com.v2java.dispatcher.task;

import com.v2java.dispatcher.dao.TaskPO;
import com.v2java.util.SpringUtil;
import lombok.*;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.Random;

/**
 * 因为Runable不交给bean管理，所以内部传入一个bean执行，业务方法封装到其中
 * @author liaowenxing 2023/7/24
 **/

@Data
@RequiredArgsConstructor
public class DispatchRunnable implements Runnable {

    @NonNull
    private TaskPO task;

    @NonNull
    private Dispatcher dispatcher;

    private int retry = 0;

    @Override
    public void run() {
        dispatcher.dispatch(task);
    }

    public void increRetry(){
        retry++;
    }
}
