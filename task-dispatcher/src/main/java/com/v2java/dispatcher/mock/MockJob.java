package com.v2java.dispatcher.mock;

import com.v2java.dispatcher.TaskRequest;
import com.v2java.dto.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author liaowenxing 2023/7/13
 **/
@Component
@Slf4j
@ConditionalOnProperty(name = "mock.job",havingValue = "true")
public class MockJob {

    @Autowired
    RestTemplate restTemplate;

    /**
     * 模拟每秒向服务创建任务
     */
    @Scheduled(fixedRate = 1000)
    public void mockProduceTask(){
        TaskRequest request = new TaskRequest();
        request.setTaskType("1");
        Response response = restTemplate.postForObject(
                "http://localhost:8989/task/commit",request
                , Response.class);
        //log.info("task commit res:{}",response);
    }

    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate(new SimpleClientHttpRequestFactory());
    }
}
