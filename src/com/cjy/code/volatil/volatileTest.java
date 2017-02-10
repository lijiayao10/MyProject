package com.cjy.code.volatil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import sun.misc.Unsafe;

public class volatileTest {

    //    static volatile int    x;

    static AtomicInteger   x               = new AtomicInteger(0);

    static ExecutorService executorService = Executors
            .newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);

    static int             POOL            = 9;

    static int             COUNT           = 10000000;

    public static void main(String[] args) throws Exception {

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    forTest(POOL, COUNT);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    forTest(1, 100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

    private static void forTest(int pool, final int count) throws InterruptedException {
        long time = System.currentTimeMillis();

        final MyCountDownLatch countDownLatch = new MyCountDownLatch(pool);

        for (int i = 0; i < pool; i++) {
            executorService.execute(new Runnable() {

                @Override
                public void run() {
                    for (int j = 0; j < count; j++) {
                        x.incrementAndGet();
                    }

                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        //        while (executorService.awaitTermination(1l, TimeUnit.SECONDS)) {
        //            System.out.println("X:" + x);
        //        }

        //        while (!executorService.isTerminated()) {
        //            System.out.println("X:" + x);
        //        }

        //        executorService.shutdown();

        // ����һ��˳��رգ�ִ����ǰ�ύ�����񣬵�������������
        //        executorService.shutdown();
//        dsdsdadsa
//大多数大
        //        Thread.sleep(10 * 1000);

        System.out.println("����ִ�С�ִ��ʱ��:" + (System.currentTimeMillis() - time));

        System.out.println("X1:" + x);

        Unsafe ddd = Unsafe.getUnsafe();
    }

}
