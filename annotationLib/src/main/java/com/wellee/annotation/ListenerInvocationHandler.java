package com.wellee.annotation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : liwei
 * 创建日期 : 2019/12/20 13:10
 * 邮   箱 : liwei@worken.cn
 * 功能描述 :
 */
public class ListenerInvocationHandler implements InvocationHandler {

    private Object target;

    private Map<String, Method> methodMap = new HashMap<>();

    public ListenerInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (target != null) {
            String methodName = method.getName();
            method = methodMap.get(methodName);
            if (method != null) {
                return method.invoke(target, args);
            }
        }
        return null;
    }

    /**
     * 添加方法至map
     * @param methodName 原方法名
     * @param method     替换后执行的方法
     */
    public void addMethod(String methodName, Method method) {
        methodMap.put(methodName, method);
    }
}
