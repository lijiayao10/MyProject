package com.cjy.code.prime;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * ��PrimeNumber.java��ʵ��������TODO ��ʵ������
 * 
 * @author Administrator 2016��1��14�� ����7:11:50
 */
public class PrimeNumber {

    private final static long              MAX        = 100_000_000;

    private final static int               POOL_MAX   = Runtime.getRuntime().availableProcessors() + 1;

    private static ExecutorService         service    = Executors.newFixedThreadPool(POOL_MAX);

    private static CompletionService<Long> service2   = new ExecutorCompletionService<Long>(service);

    private static AtomicLong              totalPrime = new AtomicLong();

    public static void main(String[] args) throws InterruptedException {

        long time = System.currentTimeMillis();
        final long count = MAX / POOL_MAX;

        for (long i = 0; i < POOL_MAX; i++) {

            final long temp = count * i;

            primeRunnableTask(count, temp);

        }
        service.shutdown();
        service.awaitTermination(MAX, TimeUnit.SECONDS);
        System.out.println("end,totalPrime:" + totalPrime);

        System.err.println("time:" + ((System.currentTimeMillis() - time) / 1000) + "s");

    }

    private static void primeRunnableTask(final long count, final long temp) {
        service.execute(new Runnable() {

            @Override
            public void run() {
                long result = 0;
                for (long j = temp; j < temp + count; j++) {
                    if (isPrime(j)) {
                        ++result;
                    }
                }

                totalPrime.addAndGet(result);

            }
        });
    }

    private static void primeRunnableTask1(final long count, final long temp) {
        service2.submit(new Callable<Long>() {

            @Override
            public Long call() throws Exception {
                long result = 0;
                for (long j = temp; j < temp + count; j++) {
                    if (isPrime(j)) {
                        ++result;
                    }
                }
                return result;
            }
        });
    }

    public static boolean isPrime(long n) {
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
