package com.cjy.code.filechannel;

import java.io.File;
import java.io.RandomAccessFile;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

import sun.nio.ch.FileChannelImpl;

public class TestDeleteMappedFile2 {

    public static void main(String args[]) {
        File f = new File("mapfile");
        RandomAccessFile aFile = null;
        FileChannel inChannel = null;
        try {
            aFile = new RandomAccessFile(f, "rw");
            inChannel = aFile.getChannel();
            ByteBuffer buf = inChannel.map(MapMode.READ_WRITE, 0L, 4);
            // 加上这几行代码,手动unmap
            Method m = FileChannelImpl.class.getDeclaredMethod("unmap", MappedByteBuffer.class);
            m.setAccessible(true);
            m.invoke(FileChannelImpl.class, buf);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inChannel.close();
                aFile.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("delete " + f + " : " + f.delete());
    }
}
