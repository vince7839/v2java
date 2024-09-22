package com.example.dubboconsumer;

import com.v2java.provider.api.ProviderService;
import com.v2java.provider.dto.ProviderRequest;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;

@Service
public class ConsumerProcessor {

    @DubboReference
    @LoadBalanced
    ProviderService providerService;

    public String hello(String name){
        ProviderRequest request = new ProviderRequest(name);
        return providerService.hello(request);
    }

    public String bye(String name){
        ProviderRequest request = new ProviderRequest(name);
        return providerService.hello(request);
    }
}
