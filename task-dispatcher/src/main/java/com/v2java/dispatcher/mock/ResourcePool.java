package com.v2java.dispatcher.mock;

import com.v2java.util.SpringUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@Data
public class ResourcePool implements Runnable {

    @Value("${mock.rpa.count}")
    Integer RPA_MOCK_COUNT;

    Map<String,MockRpa> rpaMap = new HashMap<>();

    @Autowired
    @Qualifier("rpaThreadPool")
    ExecutorService executorService;


    @PostConstruct
    public void init() {
        for (int i = 0; i < RPA_MOCK_COUNT; i++) {
            String rpaId = String.valueOf(i);
            MockRpa mockRpa = (MockRpa) Enhancer.create(MockRpa.class,new CrashInterceptor());
            mockRpa.setRpaId(rpaId);
            rpaMap.put(rpaId,mockRpa);
        }

        new Thread(this).start();
    }


    public MockRpa getMock(String rpaId){
        return rpaMap.get(rpaId);
    }


    @Override
    public void run() {
        while (true) {
            rpaMap.values().forEach(i -> executorService.execute(i));
        }
    }

    @Bean("rpaThreadPool")
    public ExecutorService threadPool() {
        return new ThreadPoolExecutor(3, 3, 0, TimeUnit.SECONDS
                , new ArrayBlockingQueue<>(100), new ThreadFactory() {
            int i = 0;
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("rpa-mock-thread-"+i++);
                return thread;
            }
        }, new ThreadPoolExecutor.DiscardPolicy());
    }
}
