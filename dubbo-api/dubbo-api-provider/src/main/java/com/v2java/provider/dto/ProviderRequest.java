package com.v2java.provider.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.io.Serializable;
@Data
@AllArgsConstructor
public class ProviderRequest implements Serializable {
    private String code;
}
