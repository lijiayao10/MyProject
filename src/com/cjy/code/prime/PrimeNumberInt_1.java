package com.cjy.code.prime;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 类PrimeNumber.java的实现描述：TODO 类实现描述
 * 
 * @author Administrator 2016年1月14日 下午7:11:50
 */
public class PrimeNumberInt_1 {

    private final static int     MAX        = 100_000_000;

    private final static int     POOL_MAX   = Runtime.getRuntime().availableProcessors() + 1;

    private static AtomicInteger totalPrime = new AtomicInteger();

    private static AtomicInteger overNumber = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {

        long time = System.currentTimeMillis();
        final int count = MAX / POOL_MAX;

        for (int i = 0; i < POOL_MAX; i++) {

            final int temp = count * i;

            primeRunnableTask(count, temp);

        }

        while (!overNumber.compareAndSet(POOL_MAX, MAX))
            ;

        System.out.println("end :" + overNumber.get() + ",totalPrime:" + totalPrime);

        System.err.println("time:" + ((System.currentTimeMillis() - time) / 1000) + "s");

    }

    private static void primeRunnableTask(final int count, final int temp) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                int result = 0;
                for (int j = temp; j < temp + count; j++) {
                    if (isPrime(j)) {
                        ++result;
                    }
                }

                totalPrime.addAndGet(result);

                //
                overNumber.incrementAndGet();

            }
        }).start();
    }

    //    private static void primeRunnableTask1(final int count, final int temp) {
    //        service2.submit(new Callable<Long>() {
    //
    //            @Override
    //            public Long call() throws Exception {
    //                int result = 0;
    //                for (int j = temp; j < temp + count; j++) {
    //                    if (isPrime(j)) {
    //                        ++result;
    //                    }
    //                }
    //                return result;
    //            }
    //        });
    //    }

    public static boolean isPrime(int n) {
        if (n <= 3) {
            return n > 1;
        }
        if (n % 2 == 0 || n % 3 == 0) {
            return false;
        }

        for (int i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) {
                return false;
            }
        }
        return true;
    }

}
