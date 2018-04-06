package com.JavaDemo.DynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Author: zhuminming
 * @create: 2018/3/24 11:16
 * @GitHubAddress: https://github.com/zhuminming
 */
public class DemoInvocationHandler implements InvocationHandler {

    private Object target;
    public DemoInvocationHandler(Object target){
        this.target=target;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("proxy invoke!!");
        method.invoke(target,args);
        return null;
    }
}
