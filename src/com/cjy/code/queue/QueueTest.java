package com.cjy.code.queue;

import java.util.concurrent.ArrayBlockingQueue;

public class QueueTest {

    public QueueTest() {
        // TODO Auto-generated constructor stub
    }
    
    private static ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(10, true);
    
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 11; i++) {
            queue.put("i:"+i);
        }
    }

}
