package com.cjy.code.cyclic;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;

public class CyclicBarrierTest {

    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {

        final AtomicBoolean atomicBoolean = new AtomicBoolean(true);

        final CyclicBarrier barrier = new CyclicBarrier(10, new Runnable() {

            @Override
            public void run() {
                System.out.println("GO GO !!!!!");
                atomicBoolean.set(true);

            }
        });

        for (int i = 0; i < 1000; i++) {

            final int tmp = i;
            new Thread(new Runnable() {

                @Override
                public void run() {
                    try {

                        System.out.println("我的编号是:" + tmp);
                        //                        isfirst = true;
                        barrier.await();

                        //                        synchronized (lock) {
                        //                            if (isfirst) {
                        //                                isfirst = false;
                        //                                System.out.println(tmp1 + ",我是这一个关卡最佳:" + tmp);
                        //                            }
                        //                        }
                        //                        System.out.println("ThreadName:" + Thread.currentThread().getName());
                        //                        if (first.compareAndSet(tmp1.get(), tmp)) {
                        //                            System.out.println(tmp1 + ",我是这一个关卡最佳:" + tmp);
                        //                            tmp1.set(first.get());
                        //                            ;
                        //                        }

                        if (atomicBoolean.compareAndSet(true, false)) {
                            System.out.println("我是这一个关卡最佳:" + (tmp % 10));
                        }

                    } catch (InterruptedException | BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            //            Thread.sleep(100l);

        }

    }

}
