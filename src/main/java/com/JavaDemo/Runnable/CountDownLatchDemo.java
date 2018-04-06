package com.JavaDemo.Runnable;

import java.util.concurrent.CountDownLatch;

/**
 * @Author: zhuminming
 * @create: 2018/3/25 12:51
 * @GitHubAddress: https://github.com/zhuminming
 */
public class CountDownLatchDemo {
    public static void main(String[] args){
        CountDownLatch begin = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(3);
        for(int i=0;i<3;i++){
            Thread thread = new Thread(new Player(begin,end));
            thread.start();
        }

        try {
            System.out.println("the race begin ");
            begin.countDown();
            System.out.println("...... ");
            end.await();
            System.out.println("the race end ");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}


class Player implements Runnable{
    private CountDownLatch begin;
    private CountDownLatch end;
    public Player(CountDownLatch begin,CountDownLatch end){

        this.begin=begin;
        this.end =end;
    }
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
            begin.await();
            System.out.println(Thread.currentThread().getName() + " arrived !");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        end.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

