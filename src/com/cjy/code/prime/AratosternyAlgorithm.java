package com.cjy.code.prime;

/**
 * @author jxqlovedn ������˹��������ɸѡ������ο���http://zh.wikipedia.org/zh-cn/������˹����ɸ��
 */
public class AratosternyAlgorithm {

    public static void getPrimes(int n) {

        int[] array = new int[n]; // �����ʼ������������������ĳ����������������ֵΪ0�������һ����Ϊ������ôarray[0]Ϊ0
        array[0] = 1; // 0��������
        array[1] = 1; // 1��������
        // ������ɸѡ���Ĺ���
        for (int i = 2; i < Math.sqrt(n); i++) { // ����С����2��ʼ
            if (array[i] == 0) {
                for (int j = i * i; j < n; j += i) {
                    array[j] = 1; // ��ʶ��λ��Ϊ������
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
