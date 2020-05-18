package com.wellee.annotation.annotations;

import com.wellee.annotation.recyclerView.CustomRecyclerView;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : liwei
 * 创建日期 : 2019/12/20 14:26
 * 邮   箱 : liwei@worken.cn
 * 功能描述 : for RecyclerView itemlongclick
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@EventBase(listenerSetter = "setOnItemLongClickListener", listenerType = CustomRecyclerView.OnItemLongClickListener.class, callBackListener = "onItemLongClick")
public @interface OnItemLongClick {
    int[] value();
}
