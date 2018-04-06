package com.JavaDemo.Runnable;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: zhuminming
 * @create: 2018/3/25 14:36
 * @GitHubAddress: https://github.com/zhuminming
 */
public class CyclicBarrierDemo {
    public static void main(String[] args){
        CyclicBarrier barrier = new CyclicBarrier(6, new Runnable() {
            @Override
            public void run() {
                System.out.println("当前线程"+Thread.currentThread().getName());
            }
        });
        ExecutorService exec = Executors.newCachedThreadPool();
        for(int i =0 ; i< 3 ; i++){
            exec.submit(new Write(barrier));
        }
        for(int i =0 ; i< 3 ; i++){
            exec.submit(new Write(barrier));
        }
        System.out.println("......end......");

    }
}

class Write implements Runnable{
    private CyclicBarrier barrier ;
    public Write(CyclicBarrier barrier){
        this.barrier = barrier;
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
        System.out.println("线程"+Thread.currentThread().getName()+"正在写入数据...");
        try {
            Thread.sleep(5000);
            System.out.println("线程"+Thread.currentThread().getName()+"写入数据完毕，等待其他线程写入完毕");
            barrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println("所有线程写入完毕，继续处理其他任务...");
    }
}
