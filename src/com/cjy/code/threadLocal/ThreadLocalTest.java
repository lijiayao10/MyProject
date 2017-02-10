package com.cjy.code.threadLocal;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadLocalTest {

    private static ExecutorService  executorService = Executors
            .newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);

    private static int              COUNT           = 10;

    private static int              POOL            = 10;

    private static ThreadLocalEvent localEvent      = new ThreadLocalEvent();

    public static void main(String[] args) throws Exception {

        final CountDownLatch countDownLatch = new CountDownLatch(POOL);

        for (int i = 0; i < POOL; i++) {
            executorService.execute(new Runnable() {

                @Override
                public void run() {
                    for (int j = 0; j < COUNT; j++) {
                        localEvent.add();

                    }

                    System.out.println(Thread.currentThread().getName() + ":start end");
                    countDownLatch.countDown();
                }
            });
        }

        System.out.println("pool end;");

        countDownLatch.await();

        System.out.println("result:" + localEvent.get());

        executorService.shutdown();

    }

    public static class ThreadLocalEvent {
        ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>();

        public ThreadLocalEvent() {
            threadLocal.set(0);
        }

        public void add() {
            if (threadLocal.get() != null)
                threadLocal.set(threadLocal.get() + 1);
            else
                threadLocal.set(0);
        }

        public Integer get() {
            System.out.println(Thread.currentThread().getName() + ":" + threadLocal.get());
            return threadLocal.get();
        }
    }
}
