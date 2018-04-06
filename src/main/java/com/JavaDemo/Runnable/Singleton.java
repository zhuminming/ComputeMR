package com.JavaDemo.Runnable;

/**
 * @Author: zhuminming
 * @create: 2018/3/11 10:00
 * @GitHubAddress: https://github.com/zhuminming
 */
public class Singleton {
    public static volatile Singleton singleton;
    private Singleton(){};
    public static Singleton getInstance(){
       if(singleton ==null){
           synchronized(singleton){
               if(singleton==null){
                   singleton = new Singleton();
               }
           }
       }
        return singleton;
    }
}
