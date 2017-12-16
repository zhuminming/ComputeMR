package com.JavaDemo.classloading;

/**
 * @Author: zhuminming
 * @create: 2017/12/16 14:34
 * @GitHubAddress: https://github.com/zhuminming
 */
public class Superclass {
    static{
        System.out.println("superclass init!");
    }
    /*
    * 知识点：常量在编译阶段会存入调用类的常量池中，本质上并没有直接引用到定义常量的类，因此不会触发定义常量的类的初始化
    * */
    public static final int value =24;
    /*
    * 知识点：通过子类引用父类的静态变量，不会导致子类初始化
    * */
    public static int intvalue=123;
}
