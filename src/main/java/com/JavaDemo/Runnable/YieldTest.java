package com.JavaDemo.Runnable;

/**
 * @Author: zhuminming
 * @create: 2018/3/11 16:09
 * @GitHubAddress: https://github.com/zhuminming
 */
public class YieldTest implements  Runnable{
    @Override
    public void run() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for(int i =0 ;i<5;i++){
            System.out.println(Thread.currentThread().getName() + ": " + i);
            Thread.yield();
        }
    }

    public static void main(String[] args){
        YieldTest runn = new YieldTest();
        Thread t1 = new Thread(runn,"FirstThread");
        Thread t2 = new Thread(runn,"SecondThread");
        t1.start();
        t2.start();

    }
}
