package com.cjy.code.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorShutDown {

    private static ExecutorService exec = Executors.newFixedThreadPool(2);

    public static void main(String[] args) throws InterruptedException {

        exec.execute(new Runnable() {
            public void run() {
                while (true) {
                    System.out.println(Thread.currentThread().getName() + ", runing!!!");

                    try {
                        Thread.sleep(10000l);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    break;
                }
            }
        });

        exec.execute(new Runnable() {
            public void run() {
                while (true) {
                    System.out.println(Thread.currentThread().getName() + ", runing!!!");

                    try {
                        Thread.sleep(10000l);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    break;
                }
            }
        });

        exec.shutdown();
        System.out.println(exec.awaitTermination(2000l, TimeUnit.SECONDS));

        System.out.println("exec end.");
    }

}
