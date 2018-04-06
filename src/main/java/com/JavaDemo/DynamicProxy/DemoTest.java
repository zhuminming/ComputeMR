package com.JavaDemo.DynamicProxy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: zhuminming
 * @create: 2018/3/24 11:18
 * @GitHubAddress: https://github.com/zhuminming
 */
public class DemoTest {
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
//        ProxyDemoTestInterface demo = new ProxyDemoTestImpl();
//        Class<?> clazz = Proxy.getProxyClass(demo.getClass().getClassLoader(),demo.getClass().getInterfaces());
//
//        Constructor<?> constructor = clazz.getConstructor(new Class[]{InvocationHandler.class});
//        ProxyDemoTestInterface proxy = (ProxyDemoTestInterface)constructor.newInstance(new DemoInvocationHandler(demo));
//
////        ProxyDemoTestInterface proxy = (ProxyDemoTestInterface)Proxy.newProxyInstance(demo.getClass().getClassLoader(),demo.getClass().getInterfaces(),new DemoInvocationHandler(demo));
//
//        proxy.print();

        ConcurrentHashMap<String,String> maps = new ConcurrentHashMap<String,String>();
        for(int i =0 ;i<100;i++){
            maps.put(i+"","a");
        }
    }
}
