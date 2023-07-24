package com.v2java.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liaowenxing 2023/7/13
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> {

    private String code;
    private T data;

    public static Response success(Object data) {
        return new Response("200", data);
    }

    public static Response success() {
        return new Response("200", null);
    }
}
