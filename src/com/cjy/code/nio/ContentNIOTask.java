package com.cjy.code.nio;

import java.nio.MappedByteBuffer;

public class ContentNIOTask {

    private MappedByteBuffer buffer = null;

    public ContentNIOTask(MappedByteBuffer buffer) {
        this.buffer = buffer;
    }

    public Integer process() throws Exception {

        int sum = 0;
        for (int i = 0; i < buffer.capacity(); i = i + JavaNIOPoolMain.ForkSize) {
            sum += JavaNIOPoolMain.getContent(i, i + JavaNIOPoolMain.ForkSize, buffer);
        }

        return sum;

    }

}
