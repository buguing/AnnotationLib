package com.wellee.annotation.annotations;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : liwei
 * 创建日期 : 2019/12/20 10:20
 * 邮   箱 : liwei@worken.cn
 * 功能描述 :
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@EventBase(listenerSetter = "setOnLongClickListener", listenerType = View.OnLongClickListener.class, callBackListener = "onLongClick")
public @interface OnLongClick {
    int[] value();
}
