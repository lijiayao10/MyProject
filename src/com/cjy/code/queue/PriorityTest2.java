package com.cjy.code.queue;

import java.util.concurrent.PriorityBlockingQueue;

public class PriorityTest2 {

    public static void main(String[] args) throws InterruptedException {
        PriorityBlockingQueue<Integer> blockingQueue = new PriorityBlockingQueue<>();

        for (int i = 1000; i > 0; i--) {
            blockingQueue.add(new Integer(i));
        }

        Thread.sleep(3000l);

        //        for (Integer integer : blockingQueue) {
        //            System.out.println("out:" + integer);
        //        }
        Integer integer = null;
        while ((integer = blockingQueue.take()) != null) {
            System.out.println("out:" + integer);
        }

    }

}
