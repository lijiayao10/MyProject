package com.cjy.code.sem;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

public class BoundedHashSet<T> {

    private final Set<T>    set;
    private final Semaphore sem;

    public BoundedHashSet(int dound) {
        this.set = Collections.synchronizedSet(new HashSet<T>());
        //设置信号量长度
        sem = new Semaphore(dound, false);
    }

    public boolean add(T o) throws InterruptedException {

        //        String name = "\"\"";

        sem.acquire();
        boolean wasAdded = false;
        try {
            wasAdded = this.set.add(o);
            return wasAdded;
        } finally {
            if (!wasAdded)
                sem.release();
        }
    }

    public boolean remove(T o) {
        boolean wasRemoved = this.set.remove(o);
        if (wasRemoved)
            sem.release();
        return wasRemoved;
    }

    public static void main(String[] args) throws InterruptedException {
        final BoundedHashSet<Integer> boundedHashSet = new BoundedHashSet<>(20);

        new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 21; i++) {
                    try {
                        System.out.println("add:" + i);
                        boundedHashSet.add(i);
                        System.out.println("add end:" + i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        Thread.sleep(2000l);

        new Thread(new Runnable() {
            public void run() {
                System.out.println("isRemove:" + boundedHashSet.remove(0));
            }
        }).start();

        Thread.sleep(2000l);

        System.out.println("count" + boundedHashSet.set.size() + ",set:" + boundedHashSet.set);
    }

}
