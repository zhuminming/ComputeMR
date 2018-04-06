package com.JavaDemo.Runnable;

/**
 * @Author: zhuminming
 * @create: 2018/3/11 14:09
 * @GitHubAddress: https://github.com/zhuminming
 */
public class SynchronizedTest {
    public  void menthod1(){
        System.out.println("Menthod 1 start");
        try{
            synchronized(this){
                System.out.println("Method 1 execute");
                Thread.sleep(3000);
            }
        }catch( InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("Method 1 end");
    }

    public void menthod2(){
        System.out.println("Menthod 2 start");
        try{
            synchronized(this){
                System.out.println("Method 2 execute");
                Thread.sleep(1000);
            }
        }catch( InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("Method 2 end");
    }

    public static void main(String[] args){
        final SynchronizedTest test1 = new SynchronizedTest();

        new Thread(new Runnable() {
            @Override
            public void run() {
                test1.menthod1();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                test1.menthod2();
            }
        }).start();
    }
}
