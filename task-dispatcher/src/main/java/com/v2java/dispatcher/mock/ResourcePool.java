package com.v2java.dispatcher.mock;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Component
@Data
public class ResourcePool implements Runnable {

    @Value("${mock.rpa.count}")
    Integer RPA_MOCK_COUNT;

    List<MockRpa> rpaList = new ArrayList<>();

    @Autowired
    @Qualifier("rpaThreadPool")
    ExecutorService executorService;


    @PostConstruct
    public void init() {
        for (int i = 0; i < RPA_MOCK_COUNT; i++) {
            rpaList.add(new MockRpa());
        }
        new Thread(this).start();
    }


    @Override
    public void run() {
        while (true) {
            rpaList.forEach(i -> executorService.execute(i));
        }
    }

    @Bean("rpaThreadPool")
    public ExecutorService threadPool() {
        return new ThreadPoolExecutor(3, 3, 0, TimeUnit.SECONDS
                , new ArrayBlockingQueue<>(100), new ThreadFactory() {
            int i = 0;

            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread();
                thread.setName("rpa-mock-thread-"+i++);
                return thread;
            }
        }, new ThreadPoolExecutor.DiscardPolicy());
    }
}
