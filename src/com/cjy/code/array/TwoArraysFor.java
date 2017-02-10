package com.cjy.code.array;

public class TwoArraysFor {

    /**
     * arraysFor:247 ms<br>
     * arraysFor2:747 ms<br>
     * 
     * 二维数组的不同实现导致查询效率的差别
     * 
     * @param args
     */
    public static void main(String[] args) {
        //        String amount = "-0.01";
        //        System.out.println(amount.trim().replaceAll("-", ""));
        TwoArraysFor.arraysFor2();
    }

    public static void arraysFor() {
        long time = System.currentTimeMillis();
        int[][] as = new int[100][1000000];
        for (int i = 0; i < as.length; i++) {
            for (int j = 0; j < as[i].length; j++) {
                as[i][j] = i * j;
            }
        }

        System.out.println((System.currentTimeMillis() - time));
    }

    public static void arraysFor2() {
        long time = System.currentTimeMillis();
        int[][] as = new int[1000000][100];
        for (int i = 0; i < as.length; i++) {
            for (int j = 0; j < as[i].length; j++) {
                as[i][j] = i * j;
            }
        }

        System.out.println((System.currentTimeMillis() - time));
    }

}
