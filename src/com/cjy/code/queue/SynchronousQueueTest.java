package com.cjy.code.queue;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class SynchronousQueueTest {

    public SynchronousQueueTest() {
    }

    private static SynchronousQueue<String> queue = new SynchronousQueue<>();

    public static void main(String[] args) {

        new Thread(new Runnable() {

            @Override
            public void run() {
                int i = 0;
                try {
                    while (i++ < 100) {
                        System.out.println("add");
                        queue.offer("add" + i, 1, TimeUnit.SECONDS);
                        System.out.println("end");

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

}
