package com.v2java.dispatcher.mock;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Random;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

/**
 * @author liaowenxing 2023/7/25
 **/
@Slf4j
public class CrashInterceptor implements MethodInterceptor {

    boolean enableCrash = false;

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy)
            throws Throwable {
        if (enableCrash) {
            Crashable crashable = method.getAnnotation(Crashable.class);
            if (Objects.nonNull(crashable)) {
                int num = new Random().nextInt(100);
                boolean happen = num < crashable.ratio();
                if (happen) {
                    happen(crashable);
                }
            }
        }
        return methodProxy.invokeSuper(o, objects);
    }

    @SneakyThrows
    private void happen(Crashable crashable) {
        String type = crashable.type();
        if ("random".equals(crashable.type())) {
            int index = new Random().nextInt(2);
            type = new String[]{"exception", "block"}[index];
        }
        if ("exception".equals(type)) {
            throw new RuntimeException();
        } else if ("block".equals(type)) {
            Thread.sleep(new Random().nextInt(crashable.maxBlock()));
        }
    }
}
