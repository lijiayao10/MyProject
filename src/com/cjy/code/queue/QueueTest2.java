package com.cjy.code.queue;

import java.sql.ResultSet;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class QueueTest2<T> {
    
    static final ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(100);
    
    public static void main(String[] args) throws InterruptedException {
        
        queue.put("1");
        queue.put("2");
        queue.put("3");
        queue.put("4");
        queue.put("5");
        queue.put("6");
        
        queue.take();
        
        System.out.println(queue);
    }

}
