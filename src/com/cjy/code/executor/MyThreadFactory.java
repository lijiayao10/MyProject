package com.cjy.code.executor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyThreadFactory implements ThreadFactory {

    private final String poolName;

    public MyThreadFactory(String poolName) {
        this.poolName = poolName;
    }

    @Override
    public Thread newThread(Runnable r) {
        return new MyAppThread(r, poolName);
    }

    public static class MyAppThread extends Thread {
        public MyAppThread(Runnable runnable, String poolName) {
            super(runnable, poolName + "-" + created.incrementAndGet());

            setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {

                @Override
                public void uncaughtException(Thread t, Throwable e) {
                    log.error("name:" + t.getName() + ",error:" + e.getMessage());
                    System.out.println("dddd");
                }
            });
        }

        public final static String         DEFAULT_NAME   = "MyAppThread";

        private static final AtomicInteger created        = new AtomicInteger();
        private static final AtomicInteger alive          = new AtomicInteger();

        private static final Logger        log            = LoggerFactory.getLogger(MyAppThread.class);

        private static volatile boolean    debuglifecycle = false;

        public static boolean isDebuglifecycle() {
            return debuglifecycle;
        }

        public static void setDebuglifecycle(boolean debuglifecycle) {
            MyAppThread.debuglifecycle = debuglifecycle;
        }

        public static int getCreated() {
            return created.get();
        }

        public static int getAlive() {
            return alive.get();
        }

    }

    private static Executor exec;

    public static void main(String[] args) {
        exec = Executors.newFixedThreadPool(1, new MyThreadFactory("woshiPool"));

        exec.execute(new Runnable() {

            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
                int i = 0 / 0;
                System.out.println(i);
            }
        });

        exec.execute(new Runnable() {

            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        });

    }

}
