package com.cjy.code.nio;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

import com.cjy.code.nio.actor.ActorManager;
import com.cjy.code.nio.forkJoin.ContentForkJoinTask;
import com.cjy.code.nio.pool.ContentExecutorsPool;

public class JavaNIOPoolMain {

    //拆除颗粒
    public static final int         ForkSize        = 100000;

    //检索字符
    public static final int         C               = 'c';

    //文件路径
    public static final String      path            = "D:\\tmp\\test.txt";

    //并行输了
    public static final int         processPool     = Runtime.getRuntime().availableProcessors() * 8;

    private static RandomAccessFile randomAccessFile;

    //处理类型
    private static ProcessTypeEnum  processTypeEnum = ProcessTypeEnum.ACTOR;

    /**
     * @param args
     * @throws Exception
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException, Exception {
        File file = new File(path);

        System.out.println("文件大小:" + file.length());

        try {
            randomAccessFile = new RandomAccessFile(file, "rw");
            MappedByteBuffer buffer = randomAccessFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0,
                    file.length());
            long t1 = System.currentTimeMillis();

            switch (processTypeEnum) {
                case FORK_JOIN:
                    processForkJoin(file, buffer);
                    break;
                case EXECUTORS:
                    processExecutor(buffer);
                    break;
                case NIO:
                    processNIO(buffer);
                    break;
                case ACTOR:
                    processActor(buffer);
                    break;
            }

            System.out.println("time:" + (System.currentTimeMillis() - t1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * fork/join 拆分
     * 
     * @param file
     * @param buffer
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private static void processForkJoin(File file, MappedByteBuffer buffer)
            throws InterruptedException, ExecutionException {
        //创建连接词
        ForkJoinPool forkJoinPool = new ForkJoinPool(JavaNIOPoolMain.processPool);

        //执行Forkjoin
        ForkJoinTask<Long> forkJoinTask = forkJoinPool.submit(new ContentForkJoinTask(0l, file.length(), buffer));

        System.out.println("sum:" + forkJoinTask.get());
    }

    /**
     * 线程池
     * 
     * @param buffer
     * @throws Exception
     */
    private static void processExecutor(MappedByteBuffer buffer) throws Exception {

        ContentExecutorsPool pool = new ContentExecutorsPool(buffer);

        System.out.println("sum:" + pool.process());
    }

    /**
     * NIO处理
     * 
     * @param buffer
     * @throws Exception
     */
    private static void processNIO(MappedByteBuffer buffer) throws Exception {

        ContentNIOTask pool = new ContentNIOTask(buffer);

        System.out.println("sum:" + pool.process());
    }

    /**
     * actor处理
     * 
     * @param buffer
     * @throws Exception
     */
    private static void processActor(MappedByteBuffer buffer) throws Exception {
        ActorManager manager = new ActorManager();

        manager.process(buffer);
    }

    public static int getContent(int begin, int end, ByteBuffer buffer) {

        int sum = 0;
        for (int i = begin; i < end && i < buffer.capacity(); i++) {
            if (buffer.get(i) == JavaNIOPoolMain.C) {
                sum++;
            }
        }
        return sum;
    }

}
