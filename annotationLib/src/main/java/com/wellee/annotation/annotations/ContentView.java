package com.wellee.annotation.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : liwei
 * 创建日期 : 2019/12/20 10:16
 * 邮   箱 : liwei@worken.cn
 * 功能描述 :
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ContentView {

    int value();
}
