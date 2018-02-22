package com.JavaDemo.Runnable;

import org.apache.zookeeper.server.SessionTrackerImpl;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: zhuminming
 * @create: 2018/2/22 20:04
 * @GitHubAddress: https://github.com/zhuminming
 */
public class SimplePriortities implements  Runnable{
    private int countDown = 5;
    private volatile double d;
    private int priority;

    public SimplePriortities(int priority){
        this.priority = priority;
    }

    public String toString(){
        return Thread.currentThread()+":"+countDown;
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
    public void run() {
        Thread.currentThread().setPriority(priority);
        while(true){
            for(int i = 1;i < 100000; i++){
                d+=(Math.PI+Math.E) /(double) i;
                if(i%1000==0){
                    Thread.yield();
                }
            }
            System.out.println(this);
            if(--countDown ==0) return;
        }
    }

    public static void main(String[] args){
        ExecutorService exec = Executors.newCachedThreadPool();
        for(int i = 0;i<5;i++){
            exec.execute(new SimplePriortities(Thread.MIN_PRIORITY));
        }
        exec.execute(new SimplePriortities(Thread.MAX_PRIORITY));
        exec.shutdown();
    }
}
