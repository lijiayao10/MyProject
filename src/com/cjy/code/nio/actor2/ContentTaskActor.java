package com.cjy.code.nio.actor2;

import java.io.Serializable;
import java.nio.ByteBuffer;

import com.cjy.code.nio.JavaNIOPoolMain;

import akka.actor.UntypedActor;

public class ContentTaskActor extends UntypedActor {

    private int msgSum = 0;

    @Override
    public void onReceive(Object arg0) throws Exception {
        Event event = (Event) arg0;

        event.setSum(getContent(event.getBegin(), event.getEnd(), event.getBuffer()));

        msgSum = event.getSum();

    }

    public int getContent(int begin, int end, ByteBuffer buffer) {

        //        System.out.println("begin:" + begin + ",end:" + end);

        int sum = 0;
        for (int i = begin; i < end && i < buffer.capacity(); i++) {
            //            System.out.print(new String(new byte[] { this.buffer.get(i) }));
            if (buffer.get(i) == JavaNIOPoolMain.C) {
                sum++;
            }
        }
        return sum;
    }

    public static class Greeting implements Serializable {
        public final String message;

        public Greeting(String message) {
            this.message = message;
        }
    }

}
