package com.cjy.code.executor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class TimingThreadPoolTest {

    public static void main(String[] args) throws InterruptedException {
        TimingThreadPool threadPool = new TimingThreadPool(2, 2, 0l, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(40));

        for (int i = 0; i < 40; i++) {
            threadPool.execute(new Runnable() {

                @Override
                public void run() {
                    try {
                        Thread.sleep(2000l);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + ":runing!!");
                }
            });
        }

        threadPool.shutdown();
        System.out.println("shutdown ing");
        threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);

        System.out.println("shutdown ing");
    }

}
