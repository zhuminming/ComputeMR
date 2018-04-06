package com.JavaDemo.Runnable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: zhuminming
 * @create: 2018/3/10 16:36
 * @GitHubAddress: https://github.com/zhuminming
 */
public class VisibilityTest {
    private volatile static int number;
    private volatile static boolean ready;

    private static class readyThread extends Thread{
        public void run(){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(!ready){
               System.out.println(ready);
            }
            System.out.println(number);
        }
    }
    private static class writerThread extends Thread{
        public void run(){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            number = 100;
            ready = true;
        }
    }

    public static void main(String[] args){
        ExecutorService exe = Executors.newCachedThreadPool();
        exe.submit(new writerThread());
        exe.submit(new readyThread());
    }
}
