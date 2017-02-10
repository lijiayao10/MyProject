package com.cjy.code.nio.forkJoin;

import java.nio.MappedByteBuffer;
import java.util.concurrent.RecursiveTask;

import com.cjy.code.nio.JavaNIOPoolMain;

public class ContentForkJoinTask extends RecursiveTask<Long> {

    private MappedByteBuffer buffer = null;

    private long             begin;
    private long             end;

    public ContentForkJoinTask(long begin, long end, MappedByteBuffer buffer) {
        this.begin = begin;
        this.end = end;
        this.buffer = buffer;
    }

    @Override
    protected Long compute() {
        try {
            //执行任务
            if ((end - begin) <= JavaNIOPoolMain.ForkSize) {
                return (long) JavaNIOPoolMain.getContent((int) begin, (int) end, buffer);
            } else {

                long newbegin = (end + begin) / 2;

                //分解内容
                ContentForkJoinTask leftTask = new ContentForkJoinTask(begin, newbegin, buffer);
                ContentForkJoinTask reftTask = new ContentForkJoinTask(newbegin, end, buffer);

                leftTask.fork();
                reftTask.fork();

                return reftTask.join() + leftTask.join();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0l;
    }
}
