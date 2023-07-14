package com.v2java.dispatcher.mock;

import com.v2java.dispatcher.TaskCommitResponse;
import com.v2java.dispatcher.TaskRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author liaowenxing 2023/7/13
 **/
@Component
@Slf4j
public class MockJob {

    @Autowired
    RestTemplate restTemplate;

    //毫秒
    @Scheduled(fixedRate = 1000)
    public void mockProduceTask(){
        TaskRequest request = new TaskRequest();
        request.setTaskType("1");
        ResponseEntity<TaskCommitResponse> entity = restTemplate.postForEntity(
                "http://localhost:8999/task/commit",request
                , TaskCommitResponse.class);
        log.info("task commit res:{}",entity.getBody());
    }

    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate(new SimpleClientHttpRequestFactory());
    }
}
