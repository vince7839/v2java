package com.v2java.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author liaowenxing 2023/7/24
 **/
public class SpringUtil implements ApplicationContextAware {

    public static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtil.applicationContext = applicationContext;
    }

    public static  <T> T getBean(Class<T> clazz){
        return applicationContext.getBean(clazz);
    }
}
