package com.v2java.provider.api;

import com.v2java.provider.dto.ProviderRequest;

public interface ProviderService {

    String hello(ProviderRequest request);

    String bye(ProviderRequest request);
}
