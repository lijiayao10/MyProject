package com.cjy.code.retry;

import scala.util.Random;

/**
 * ��RetryTest.java��ʵ��������TODO ��ʵ������
 * 
 * @author caojiayao<20160114> 2016��1��14�� ����4:50:08
 */
public class RetryTest {

    public static void main(String[] args) {

        Random random = new Random();

        int j = 0;
        aaa: while (j++ < 10000) {
            System.out.println("out:" + j);
            if (j == 9999) {
                bbb: for (;;) {
                    System.out.println("out2:" + j);
                    if (random.nextInt(10000) == 9999)
                        break bbb;
                    if (random.nextInt(10000) == 9998)
                        continue aaa;
                }
            }
        }

        System.out.println("end");
    }

}
