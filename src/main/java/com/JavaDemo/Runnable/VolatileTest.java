package com.JavaDemo.Runnable;

import scala.collection.generic.VolatileAbort;

/**
 * @Author: zhuminming
 * @create: 2018/3/11 10:16
 * @GitHubAddress: https://github.com/zhuminming
 */
public class VolatileTest {
    int a = 1;
    int b = 2;
    public void change(){
        a=3;
        b=a;
    }

    public void print(){
        System.out.println("b="+b+";a="+a);
    }

    public static void main(String[] args){
        while(true){
            final VolatileTest test=new VolatileTest();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    test.change();
                }
            }).start();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    test.print();
                }
            }).start();
        }
    }
}
