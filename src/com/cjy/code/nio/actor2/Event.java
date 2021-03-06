package com.cjy.code.nio.actor2;

import java.nio.ByteBuffer;

public class Event {

    private ByteBuffer buffer;

    private int        begin;

    private int        end;

    private int        sum;

    public Event(int begin, int end, ByteBuffer buffer2) {
        this.begin = begin;
        this.end = end;
        this.buffer = buffer2;
    }

    public ByteBuffer getBuffer() {
        return buffer;
    }

    public void setBuffer(ByteBuffer buffer) {
        this.buffer = buffer;
    }

    public int getBegin() {
        return begin;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}
