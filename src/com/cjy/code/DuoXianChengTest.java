package com.cjy.code;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DuoXianChengTest {

    private static ExecutorService POOL = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);

    //    private static ExecutorService POOL = Executors.newFixedThreadPool(1000);

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        System.out.println(Runtime.getRuntime().availableProcessors());

        //        Future<Integer> futureTask = POOL.submit(new Callable<Integer>() {
        //
        //            @Override
        //            public Integer call() throws Exception {
        //                int i = 0;
        //                while (i++ < 100) {
        //
        //                }
        //                return i;
        //            }
        //        });
        //
        //        System.out.println(futureTask.get());

        final int xishu = 10000;

        int poolcount = 1000;

        //        final ArrayList<Integer> list = new ArrayList<Integer>(poolcount * xishu);

        long times = System.currentTimeMillis();

        final Integer[] list = new Integer[poolcount * xishu];

        final CountDownLatch countDownLatch = new CountDownLatch(poolcount);
        for (int i = 0; i < poolcount; i++) {
            final int currQuertTime = i;

            System.out.println("i:" + i);

            POOL.submit(new Runnable() {

                @Override
                public void run() {
                    try {
                        System.out.println("threadName:" + Thread.currentThread().getName());
                        int j = currQuertTime * xishu;
                        while (j < (currQuertTime * xishu) + xishu) {
                            //                            System.out.println(Thread.currentThread().getName() + ",j:" + j);
                            list[j] = j;
                            //                            list.add(j);
                            j++;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        countDownLatch.countDown();
                    }
                }
            });

        }

        countDownLatch.await();

        List<Integer> arrays = new ArrayList<Integer>(Arrays.asList(list));
        //        Collections.sort(list);

        //        for (Integer i : arrays) {
        //            System.out.print(i + ",");
        //        }
        System.out.println("arrays size :" + arrays.size());
        System.out.println("times: " + (System.currentTimeMillis() - times));

        POOL.shutdown();

    }
}
