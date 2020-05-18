package com.wellee.annotation;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.wellee.annotation.annotations.ContentView;
import com.wellee.annotation.annotations.EventBase;
import com.wellee.annotation.annotations.InjectView;
import com.wellee.annotation.annotations.ItemView;
import com.wellee.annotation.recyclerView.CustomRecyclerView;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author : liwei
 * 创建日期 : 2019/12/20 10:09
 * 邮   箱 : liwei@worken.cn
 * 功能描述 :
 */
public class InjectManager {

    public static void inject(Activity activity) {
        // 布局注入
        injectLayout(activity);
        // 控件注入
        injectViews(activity);
        // 事件注入
        injectEvents(activity);
    }

    public static void injectRecyclerViewItems(Context context, CustomRecyclerView.BaseAdapter adapter) {
        if (adapter != null) {
            Class<? extends RecyclerView.Adapter> clazz = adapter.getClass();
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field field : declaredFields) {
                ItemView itemView = field.getAnnotation(ItemView.class);
                if (itemView != null) {
                    int layoutId = itemView.value();
                    View view = View.inflate(context, layoutId, null);
                    try {
                        field.set(adapter, view);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void injectRecyclerViewHolder(RecyclerView.ViewHolder viewHolder) {
        if (viewHolder != null) {
            Class<? extends RecyclerView.ViewHolder> clazz = viewHolder.getClass();
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field field : declaredFields) {
                InjectView injectView = field.getAnnotation(InjectView.class);
                if (injectView != null) {
                    int viewId = injectView.value();
                    View view = viewHolder.itemView.findViewById(viewId);
                    try {
                        field.set(viewHolder, view);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static void injectLayout(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        ContentView contentView = clazz.getAnnotation(ContentView.class);
        if (contentView != null) {
            int layoutId = contentView.value();
            // 方式1：直接设置
            // activity.setContentView(layoutId);
            try {
                // 方式二：反射
                Method method = clazz.getMethod("setContentView", int.class);
                method.invoke(activity, layoutId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void injectViews(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            InjectView injectView = field.getAnnotation(InjectView.class);
            if (injectView != null) {
                int viewId = injectView.value();
                // 方式一：
                // View view = activity.findViewById(viewId);
                try {
                    // 方式二
                    Method method = clazz.getMethod("findViewById", int.class);
                    Object view = method.invoke(activity, viewId);
                    field.setAccessible(true);
                    field.set(activity, view);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private static void injectEvents(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        // 获取所有方法
        Method[] methods = clazz.getDeclaredMethods();
        // 遍历方法
        for (Method method : methods) {
            // 获取每个方法的注解
            Annotation[] annotations = method.getAnnotations();
            // 遍历注解
            for (Annotation annotation : annotations) {
                Class<? extends Annotation> annotationType = annotation.annotationType();
                if (annotationType != null) {
                    EventBase eventBase = annotationType.getAnnotation(EventBase.class);
                    if (eventBase != null) {
                        // 获取事件重要三成员
                        String listenerSetter = eventBase.listenerSetter();
                        Class<?> listenerType = eventBase.listenerType();
                        String callBackListener = eventBase.callBackListener();

                        // 动态代理 拦截原有onClick/onLongClick方法 并执行当前注解的方法
                        ListenerInvocationHandler invocationHandler = new ListenerInvocationHandler(activity);
                        invocationHandler.addMethod(callBackListener, method);

                        Object listener = Proxy.newProxyInstance(listenerType.getClassLoader(), new Class[]{listenerType}, invocationHandler);

                        try {
                            // 获取自定义onClick/onLongClick注解的value值
                            Method valueMethod = annotationType.getDeclaredMethod("value");
                            // 执行获取
                            int[] viewIds = (int[]) valueMethod.invoke(annotation);
                            for (int viewId : viewIds) {
                                // 获取当前activity的view
                                View view = activity.findViewById(viewId);
                                // 获取指定方法
                                Method setter = view.getClass().getMethod(listenerSetter, listenerType);
                                // 设置view的监听
                                setter.invoke(view, listener);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
