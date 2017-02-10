package com.cjy.code.prime;

public class MyPrimeNum {

    public static int jisuan(int beishu, int max) {

        for (int i = beishu; i < max; i += beishu) {

        }

        return 0;
    }

    public static int getPrimes(int n) {

        boolean[] array = new boolean[n + 1]; // �����ʼ������������������ĳ����������������ֵΪ0�������һ����Ϊ������ôarray[0]Ϊ0

        for (int i = 3; i <= n; i += 2) {
            array[i] = true;
        }

        array[0] = false; // 0��������
        array[1] = false; // 1��������
        // ������ɸѡ���Ĺ���
        for (int i = 2; i < Math.sqrt(n); i++) { // ����С����2��ʼ
            if (array[i]) {
                for (int j = i * i; j < n; j += i) {
                    array[j] = false; // ��ʶ��λ��Ϊ������
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
