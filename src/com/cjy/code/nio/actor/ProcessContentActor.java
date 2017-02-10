package com.cjy.code.nio.actor;

import java.nio.ByteBuffer;

import com.cjy.code.nio.JavaNIOPoolMain;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

public class ProcessContentActor extends UntypedActor {

    private int getContent(int begin, int end, ByteBuffer buffer) {

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

    //´òÓ¡½á¹ûactor
    private ActorRef printSumActor;

    public ProcessContentActor(ActorRef printSumActor) {
        this.printSumActor = printSumActor;
    }

    public void onReceive(Object message) {
        if (message instanceof Event) {
            Event event = (Event) message;
            int result = getContent(event.getBegin(), event.getEnd(), event.getBuffer());

            printSumActor.tell(new PrintSum(result, (event.getBuffer().capacity() / JavaNIOPoolMain.ForkSize) + 1),
                    getSelf());

        } else {
            unhandled(message);
        }
    }
}
