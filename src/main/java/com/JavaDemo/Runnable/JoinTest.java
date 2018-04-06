package com.JavaDemo.Runnable;

/**
 * @Author: zhuminming
 * @create: 2018/3/11 16:22
 * @GitHubAddress: https://github.com/zhuminming
 */
public class JoinTest implements Runnable{
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " start-----");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " end-----");
    }


    public static void main(String[] args) throws InterruptedException {

        for(int i =0;i<5;i++){
            Thread test = new Thread(new JoinTest());
            test.start();
            test.join();
        }
        Thread.sleep(100);
        System.out.println("Finished~~~");
    }
}
