package com.cjy.code.queue;

import java.util.concurrent.PriorityBlockingQueue;

public class PriorityTest {

    public static void main(String[] args) throws InterruptedException {
        PriorityBlockingQueue<IntegerComparable> blockingQueue = new PriorityBlockingQueue<>();

        for (int i = 0; i < 1000; i++) {
            blockingQueue.add(new IntegerComparable(i));
        }

        //        for (IntegerComparable integer : blockingQueue) {
        //            System.out.println("out:" + integer);
        //        }

        IntegerComparable integer = null;
        while ((integer = blockingQueue.take()) != null) {
            System.out.println("out:" + integer);
        }

    }

    public static class IntegerComparable implements Comparable<IntegerComparable> {
        private Integer i = 0;

        public IntegerComparable(Integer i) {
            this.i = i;
        }

        @Override
        public int compareTo(IntegerComparable o) {

            //            int x = new Integer(i % 3).compareTo(new Integer(o.i % 3));

            int x = i.compareTo(o.i);

            if (x != 0 && this != o) {
                int x1 = i % 3;
                int x2 = o.i % 3;
                if (x1 < x2) {
                    return -1;
                } else if (x1 == x2) {
                    return 0;
                } else if (x1 > x2) {
                    return 1;
                }
            }

            //            if (x == 0) {
            //                return 1;
            //            } else if (x == 1) {
            //                return 0;
            //            } else {
            //                return -1;
            //            }
            return x;

        }

        @Override
        public String toString() {
            return (i % 3) + "";
        }
    }

}
