package com.cjy.code.executor;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorTest {

    private final static int POOL_COUNT = Runtime.getRuntime().availableProcessors() + 1;

    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(POOL_COUNT, POOL_COUNT, 0l, TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<Runnable>(100));

        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    }

}
