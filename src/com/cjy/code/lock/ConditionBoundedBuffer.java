package com.cjy.code.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionBoundedBuffer<T> {

    protected final Lock    lock     = new ReentrantLock();

    private final Condition notFull  = lock.newCondition();

    private final Condition notEmpty = lock.newCondition();

    private final T[]       items;

    private int             tail, head, count;

    public ConditionBoundedBuffer() {
        this(100);
    }

    public ConditionBoundedBuffer(int capacity) {
        if (capacity <= 0)
            throw new IllegalArgumentException();
        this.items = (T[]) new Object[capacity];
    }

    public void put(T x) throws InterruptedException {
        lock.lock();
        try {
            if (count == items.length) {
                notFull.await();
            }

            items[tail] = x;

            if (++tail == items.length) {
                tail = 0;
            }

            notEmpty.signal();

            count++;

        } finally {
            lock.unlock();
        }
    }

    public T take() throws InterruptedException {
        lock.lock();
        try {
            if (count == 0) {
                notEmpty.await();
            }

            T val = items[head];

            items[head] = null;

            if (++head == items.length) {
                head = 0;
            }

            notFull.signal();

            count--;
            return val;
        } finally {
            lock.unlock();
        }
    }

    private final static int COUNT = 500;

    public static void main(String[] args) throws InterruptedException {
        final ConditionBoundedBuffer<String> buffer = new ConditionBoundedBuffer<>();

        new Thread(new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < COUNT; i++) {
                    try {
                        String str = "i=" + i;
                        System.out.println("in:" + str);
                        buffer.put(str);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        ;

        new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    try {
                        System.out.println("out:" + buffer.take());
                        Thread.sleep(1000l);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        ;
    }

}
