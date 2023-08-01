package com.v2java.fs;

/**
 * @author liaowenxing 2023/8/1
 **/
public interface MessageProcessor<T> {

    String getType();

    void process(T t);
}
