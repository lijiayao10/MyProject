package com.cjy.code.prime;

public class MyPrimeNum2 {

    public static int getPrimes(int n) {

        //true 非素数  false 是素数

        boolean[] primes = new boolean[n + 1];

        //0无效,1,2 非素数
        primes[0] = true;
        primes[1] = true;
        primes[2] = true;

        //平方Math.sqrt(n)

        for (int i = 4; i <= n / 2; i += 2) {
            primes[i] = true;
        }

        for (int i = 3; i <= n / 2; i += 2) {
            //            primes[i] = true;
            if (!primes[i]) {
                for (int j = i * 2; j <= n; j += i) {
                    primes[j] = true;
                }
            }
        }

        int result = 0;
        for (int i = 3; i < primes.length; i++) {
            if (!primes[i]) {
                result++;
            }
        }

        return result;
    }

    public static void main(String[] args) {
        long time = System.currentTimeMillis();

        System.out.println("end,totalPrime:" + getPrimes(1_0000_0000));

        System.err.println("time:" + ((System.currentTimeMillis() - time)) + "s");
    }

}
