package com.cjy.code.prime;

public class Prime2 {
    static final int Nmax  = 50;
    static int[]     prime = new int[Nmax];

    public static int calculatenumber(int Nmax) {
        boolean[] isPrime = new boolean[Nmax + 1];
        isPrime[2] = true;
        prime[0] = 2;
        int totalPrimes = 1;

        for (int i = 3; i <= Nmax; i += 2) {
            isPrime[i] = true;
        }
        for (int i = 3; i <= Nmax; i += 2) {
            if (isPrime[i])
                prime[totalPrimes++] = i;

            for (int j = 1; i * prime[j] <= Nmax && j < totalPrimes; j++) {
                //                System.out.println(String.format("i=%s,j=%s,prime[j]=%s,totalProimes=%s", i, j, prime[j], totalPrimes));
                isPrime[i * prime[j]] = false;
                if (i % prime[j] == 0)
                    break;

            }
        }
        return totalPrimes;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        long time = System.currentTimeMillis();
        int primeNum = Prime2.calculatenumber(Nmax);

        System.out.println("totalPrimes:" + primeNum + ",time:" + (System.currentTimeMillis() - time));
        //        System.out.println(Arrays.toString(prime));
    }
}
