package com.JavaDemo.Runnable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @Author: zhuminming
 * @create: 2018/3/25 15:24
 * @GitHubAddress: https://github.com/zhuminming
 */
public class SemaphoreDemo {
    public static void main(String[] args){
        ExecutorService exec = Executors.newCachedThreadPool();
        Semaphore semaphore = new Semaphore(5);
        for(int i=0;i<3;i++){
            exec.submit(new Work(i,semaphore));
        }

    }
}

class Work implements Runnable{
    private int num;
    private Semaphore semaphore;

    public Work(int num,Semaphore semaphore){
        this.semaphore = semaphore;
        this.num=num;
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
            semaphore.acquire();
            System.out.println("工人"+this.num+"占用一个机器在生产...");
            Thread.sleep(2000);
            semaphore.release();
            System.out.println("工人"+this.num+"释放出机器");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
