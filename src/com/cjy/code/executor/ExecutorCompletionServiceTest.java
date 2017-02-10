package com.cjy.code.executor;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorCompletionServiceTest {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);

        ExecutorCompletionService<String> service = new ExecutorCompletionService<String>(executorService);

        service.submit(new Work(9000l));
        service.submit(new Work(4000l));
        service.submit(new Work(8000l));
        service.submit(new Work(1000l));

        for (int i = 0; i < 4; i++) {
            System.out.println(service.take().get());
        }

    }

    static class Work implements Callable<String> {

        final long time;

        public Work(long time) {
            this.time = time;
        }

        @Override
        public String call() throws Exception {
            Thread.currentThread().sleep(time);
            return "我睡眠了这么久time:" + time + "";
        }
    }

}
