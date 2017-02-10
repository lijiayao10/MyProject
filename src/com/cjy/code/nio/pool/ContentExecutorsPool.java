package com.cjy.code.nio.pool;

import java.nio.MappedByteBuffer;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.cjy.code.nio.JavaNIOPoolMain;

public class ContentExecutorsPool {

    private MappedByteBuffer buffer = null;

    public ContentExecutorsPool(MappedByteBuffer buffer) {
        this.buffer = buffer;
    }

    public Integer process() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(JavaNIOPoolMain.processPool);

        int sum = 0;
        for (int i = 0; i < buffer.capacity(); i = i + JavaNIOPoolMain.ForkSize) {

            final int begin = i;

            Future<Integer> futureTask = executorService.submit(new Callable<Integer>() {

                @Override
                public Integer call() throws Exception {
                    return JavaNIOPoolMain.getContent(begin, begin + JavaNIOPoolMain.ForkSize, buffer);
                }
            });
            sum += futureTask.get();
        }

        //πÿ±’œﬂ≥Ã≥ÿ
        try {
            executorService.shutdown();
            if (!executorService.awaitTermination(5 * 1000, TimeUnit.MILLISECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            executorService.shutdownNow();
        }
        return sum;

    }

}
