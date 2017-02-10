package com.cjy.code.prime;

public class MyPrimeNum {

    public static int jisuan(int beishu, int max) {

        for (int i = beishu; i < max; i += beishu) {

        }

        return 0;
    }

    public static int getPrimes(int n) {

        boolean[] array = new boolean[n + 1]; // 假设初始所有数都是素数，且某个数是素数，则其值为0；比如第一个数为素数那么array[0]为0

        for (int i = 3; i <= n; i += 2) {
            array[i] = true;
        }

        array[0] = false; // 0不是素数
        array[1] = false; // 1不是素数
        // 下面是筛选核心过程
        for (int i = 2; i < Math.sqrt(n); i++) { // 从最小素数2开始
            if (array[i]) {
                for (int j = i * i; j < n; j += i) {
                    array[j] = false; // 标识该位置为非素数
                }
            }
        }

        int result = 0;
        for (int i = 2; i < array.length; i++) {
            if (array[i])
                result++;
        }

        return result;
    }

    public static void main(String[] args) {
        long time = System.currentTimeMillis();

        System.out.println("end,totalPrime:" + getPrimes(1_0000_0000));

        System.err.println("time:" + ((System.currentTimeMillis() - time)) + "s");
    }

}
