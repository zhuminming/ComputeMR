package com.JavaDemo.Runnable;

/**
 * @Author: zhuminming
 * @create: 2018/3/11 15:56
 * @GitHubAddress: https://github.com/zhuminming
 */
public class SleepTest {
    public synchronized void sleepMethod(){
        System.out.println("sleep start -----");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("sleep end -----");
    }

    public synchronized void waitMenthod(){
        System.out.println("wait start-----");
        synchronized(this){
            try {
                wait(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("wait end-----");
    }

    public static void main(String[] args){
        final SleepTest test1 = new SleepTest();
        for(int i=0;i<3;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                  test1.sleepMethod();
                }
            }).start();
        }

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("-----分割线-----");

        final SleepTest test2= new SleepTest();
        for(int i=0;i<3;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    test2.waitMenthod();
                }
            }).start();
        }
    }
}
