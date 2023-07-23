package com.v2java.dispatcher.mock;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.util.Random;

@AllArgsConstructor
abstract public class Crashable {

    private String type;

    abstract void doSth();

    public void happen(){
        crash();
        doSth();
    }

    @SneakyThrows
    public void crash(){
        int num = new Random().nextInt(100);
        boolean crash = num < 10;
        if (!crash){
            return;
        }
        if ("crash".equals(type)){
            throw new RuntimeException();
        }else if ("block".equals(type)){
            Thread.sleep(1000*10);
        }
    }
}
