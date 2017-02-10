package com.cjy.code.volatil;

import java.util.concurrent.atomic.AtomicInteger;

public class MyCountDownLatch {

    AtomicInteger atomicInteger = new AtomicInteger(-1);

    public MyCountDownLatch(int i) {
        synchronized (this) {
            atomicInteger.set(i);
        }
    }

    public int countDown() {
        return atomicInteger.decrementAndGet();
    }

    public void await() {
        while (!atomicInteger.compareAndSet(0, 0)) {
        }
    }

}
