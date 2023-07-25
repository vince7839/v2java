package com.v2java.dispatcher.mock;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Crashable {

    String type() default "random";

    int ratio() default 5;

    int maxBlock() default 1000;
}
