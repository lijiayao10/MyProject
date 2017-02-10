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

    //�������
    public static final int         ForkSize        = 100000;

    //�����ַ�
    public static final int         C               = 'c';

    //�ļ�·��
    public static final String      path            = "D:\\tmp\\test.txt";

    //��������
    public static final int         processPool     = Runtime.getRuntime().availableProcessors() * 8;

    private static RandomAccessFile randomAccessFile;

    //��������
    private static ProcessTypeEnum  processTypeEnum = ProcessTypeEnum.ACTOR;

    /**
     * @param args
     * @throws Exception
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException, Exception {
        File file = new File(path);

        System.out.println("�ļ���С:" + file.length());

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
     * fork/join ���
     * 
     * @param file
     * @param buffer
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private static void processForkJoin(File file, MappedByteBuffer buffer)
            throws InterruptedException, ExecutionException {
        //�������Ӵ�
        ForkJoinPool forkJoinPool = new ForkJoinPool(JavaNIOPoolMain.processPool);

        //ִ��Forkjoin
        ForkJoinTask<Long> forkJoinTask = forkJoinPool.submit(new ContentForkJoinTask(0l, file.length(), buffer));

        System.out.println("sum:" + forkJoinTask.get());
    }

    /**
     * �̳߳�
     * 
     * @param buffer
     * @throws Exception
     */
    private static void processExecutor(MappedByteBuffer buffer) throws Exception {

        ContentExecutorsPool pool = new ContentExecutorsPool(buffer);

        System.out.println("sum:" + pool.process());
    }

    /**
     * NIO����
     * 
     * @param buffer
     * @throws Exception
     */
    private static void processNIO(MappedByteBuffer buffer) throws Exception {

        ContentNIOTask pool = new ContentNIOTask(buffer);

        System.out.println("sum:" + pool.process());
    }

    /**
     * actor����
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
