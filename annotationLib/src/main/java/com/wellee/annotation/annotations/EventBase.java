package com.wellee.annotation.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : liwei
 * 创建日期 : 2019/12/20 10:47
 * 邮   箱 : liwei@worken.cn
 * 功能描述 :
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventBase {

    // 监听方法名 setOnClickListener()
    String listenerSetter();
    // 监听对象 View.OnClickListener
    Class<?> listenerType();
    // 方法回调名 OnClick(View v)
    String callBackListener();


}
