package com.cjy.code.prime;

public class MyPrimeNum4 {

    public static int getPrimes(int n) {
        //false 非素数  true 是素数
        boolean[] primes = new boolean[n + 1];

        int result = 1;

        //0无效,1,2 非素数
        primes[0] = false;
        primes[1] = false;
        primes[2] = false;

        //平方Math.sqrt(n)

        for (int i = 3; i <= n; i += 2) {
            primes[i] = true;
        }

        for (int i = 3; i <= n; i += 2) {
            if (primes[i]) {
                result++;
                for (int j = i * 2; j <= n; j += i) {
                    primes[j] = false;

                }
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
