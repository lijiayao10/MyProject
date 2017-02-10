package com.cjy.code.nio;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class JavaNIOTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        MappedByteBuffer buffer = null;
        try {
            buffer = new RandomAccessFile("/home/tobacco/test/res.txt", "rw").getChannel().map(
                    FileChannel.MapMode.READ_WRITE, 0, 1253244);
            int sum = 0;
            int n;
            long t1 = System.currentTimeMillis();
            for (int i = 0; i < 1253244; i++) {
                n = 0x000000ff & buffer.get(i);
                sum += n;
            }
            long t = System.currentTimeMillis() - t1;
            System.out.println("sum:" + sum + "  time:" + t);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block  
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block  
            e.printStackTrace();
        }
    }
    
    
}

