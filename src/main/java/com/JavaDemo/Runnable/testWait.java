package com.JavaDemo.Runnable;

/**
 * @Author: zhuminming
 * @create: 2018/3/11 15:39
 * @GitHubAddress: https://github.com/zhuminming
 */
public class testWait {
    public synchronized void testWait(){
        System.out.println(Thread.currentThread().getName()+"Start----");
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+"End----");
    }

    public static void main(String[] args) throws InterruptedException {
        final testWait test = new testWait();
       for(int i =0;i<5;i++){
           new Thread(new Runnable() {
               @Override
               public void run() {
                   test.testWait();
               }
           }).start();
       }
        synchronized(test){
            test.notify();
        }
        Thread.sleep(3000);
        System.out.println("------------分割线------------");
        synchronized(test){
            test.notifyAll();
        }
    }
}
