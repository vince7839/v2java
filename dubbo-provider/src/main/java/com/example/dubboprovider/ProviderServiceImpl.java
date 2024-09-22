package com.example.dubboprovider;

import com.v2java.provider.api.ProviderService;
import com.v2java.provider.dto.ProviderRequest;
import org.apache.dubbo.config.annotation.DubboService;


@DubboService
public class ProviderServiceImpl implements ProviderService {

    @Override
    public String hello(ProviderRequest request) {
        return "hello:"+request.getCode();
    }

    @Override
    public String bye(ProviderRequest request) {
        return "bye:"+request.getCode();
    }

}
