package com.cjy.code.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceTest2 {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);

        List<Work> tasks = new ArrayList<>();
        tasks.add(new Work(9000l));
        tasks.add(new Work(4000l));
        tasks.add(new Work(8000l));
        tasks.add(new Work(1000l));

        List<Future<String>> futures = executorService.invokeAll(tasks, 5l, TimeUnit.SECONDS);

        for (Future info : futures) {
            try {
                System.out.println(info.get());
            } catch (ExecutionException e) {
                System.out.println("报错:" + e.getCause());
            } catch (CancellationException e) {
                System.out.println("超时的线程+" + e);
            }
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
