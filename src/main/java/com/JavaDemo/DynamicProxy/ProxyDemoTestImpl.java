package com.JavaDemo.DynamicProxy;

/**
 * @Author: zhuminming
 * @create: 2018/3/24 11:12
 * @GitHubAddress: https://github.com/zhuminming
 */
public class ProxyDemoTestImpl implements ProxyDemoTestInterface {
    @Override
    public void print() {
        System.out.println("Hello World!!");
    }
}
