package com.cjy.code.lock;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class OneShotLatch {

    private final Sync sync = new Sync();

    public void signal() throws InterruptedException {
        sync.releaseShared(0);
    }

    public void await() throws InterruptedException {
        sync.acquireSharedInterruptibly(0);
    }

    private class Sync extends AbstractQueuedSynchronizer {
        @Override
        protected int tryAcquireShared(int ignored) {
            return (getState() == 1) ? 1 : -1;
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            setState(1);
            return true;
        }
    }
}
