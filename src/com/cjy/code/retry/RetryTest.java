package com.cjy.code.retry;

import scala.util.Random;

/**
 * 类RetryTest.java的实现描述：TODO 类实现描述
 * 
 * @author caojiayao<20160114> 2016年1月14日 下午4:50:08
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
