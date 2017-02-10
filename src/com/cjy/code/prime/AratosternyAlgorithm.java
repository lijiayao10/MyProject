package com.cjy.code.prime;

/**
 * @author jxqlovedn 埃拉托斯特尼素数筛选法，请参考：http://zh.wikipedia.org/zh-cn/埃拉托斯特尼筛法
 */
public class AratosternyAlgorithm {

    public static void getPrimes(int n) {

        int[] array = new int[n]; // 假设初始所有数都是素数，且某个数是素数，则其值为0；比如第一个数为素数那么array[0]为0
        array[0] = 1; // 0不是素数
        array[1] = 1; // 1不是素数
        // 下面是筛选核心过程
        for (int i = 2; i < Math.sqrt(n); i++) { // 从最小素数2开始
            if (array[i] == 0) {
                for (int j = i * i; j < n; j += i) {
                    array[j] = 1; // 标识该位置为非素数
                }
            }
        }
        int count = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == 0) {
                count++;
            }
        }
        System.out.println(count);
    }

    public static void main(String[] args) {

        long time = System.currentTimeMillis();

        getPrimes(1_0000_0000);

        System.err.println("time:" + ((System.currentTimeMillis() - time)) + "s");
    }
}
