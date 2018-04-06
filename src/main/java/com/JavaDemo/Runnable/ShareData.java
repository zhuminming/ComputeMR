package com.JavaDemo.Runnable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: zhuminming
 * @create: 2018/3/10 15:30
 * @GitHubAddress: https://github.com/zhuminming
 */
public class ShareData {
    private static int count = 0;

    static class countThread implements Runnable {

        /**
         * When an object implementing interface <code>Runnable</code> is used
         * to create a thread, starting the thread causes the object's
         * <code>run</code> method to be called in that separately executing
         * thread.
         * <p/>
         * The general contract of the method <code>run</code> is that it may
         * take any action whatsoever.
         *
         * @see Thread#run()
         */
        @Override
        public void run() {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < 100; i++) {
                addCount();
            }
        }
    }

    private static synchronized void addCount(){
        count++;
    }

    public static void main(String[] args){
        ExecutorService exe = Executors.newFixedThreadPool(10);
        for(int i =0;i<10;i++){
            exe.submit(new countThread());
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("count=" + count);
    }
}
