package com.v2java.dispatcher.mock;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author liaowenxing 2023/7/24
 **/
@Aspect
@Slf4j
public class CrashAspect {

    @Pointcut("execution(* com.v2java.dispatcher.mock.* (*))")
    public void pointcut(){

    }

    @Before("pointcut()")
    public void before(JoinPoint joinPoint){
        Object target = joinPoint.getTarget();
        log.info("aspect:"+target.getClass().getSimpleName());
    }
}
