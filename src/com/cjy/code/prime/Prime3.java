package com.cjy.code.prime;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Prime3 {
    static final int Nmax    = 1_0000_0000;
    static int[]     prime   = new int[Nmax];
    static boolean[] isPrime = new boolean[Nmax + 1];

    static {
        for (int i = 3; i <= Nmax; i += 2) {
            isPrime[i] = true;
        }
        isPrime[2] = true;
        prime[0] = 2;
    }

    public static int calculatenumber(int start, int Nmax) {

        System.out.println("start:" + start + ",end:" + Nmax);

        int totalPrimes = 1;
        for (int i = start + 3; i <= Nmax; i += 2) {
            if (isPrime[i])
                prime[totalPrimes++] = i;
            for (int j = 1; i * prime[j] <= Nmax && j < totalPrimes; j++) {
                isPrime[i * prime[j]] = false;
                if (i % prime[j] == 0)
                    break;

            }
        }
        return totalPrimes;
    }

    final static int             POOL_MAX  = Runtime.getRuntime().availableProcessors() + 1;

    final static ExecutorService exec      = Executors.newFixedThreadPool(1);

    //    final static int             poolCount = (Nmax - 3) / 2 / POOL_MAX;

    final static int             poolCount = Nmax / POOL_MAX;

    /**
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        long time = System.currentTimeMillis();

        final AtomicInteger totalPrime = new AtomicInteger();

        System.out.println(poolCount);

        for (int i = 0; i < POOL_MAX; i++) {
            final int tmp = i * poolCount;
            exec.execute(new Runnable() {

                @Override
                public void run() {
                    int x = tmp;
                    if (tmp == 0) {
                        x = 3;
                    }

                    int primeNum = Prime3.calculatenumber(x, tmp + poolCount);
                    totalPrime.addAndGet(primeNum);
                    System.out.println(primeNum);
                }
            });
        }
        exec.shutdown();
        exec.awaitTermination(Integer.MAX_VALUE, TimeUnit.MILLISECONDS);

        System.out.println("totalPrimes:" + totalPrime.get() + ",time:" + (System.currentTimeMillis() - time));
    }
}
