package com.cjy.code.nio.actor;

import java.nio.ByteBuffer;

import com.cjy.code.nio.JavaNIOPoolMain;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.RoundRobinPool;

public class ActorProcess extends UntypedActor {
    private final ByteBuffer buffer;

    private final ActorRef   processRouter;

    public ActorProcess(ByteBuffer buffer) {
        this.buffer = buffer;

        //打印结果Actor
        ActorRef printSumActor = getContext().system().actorOf(Props.create(PrintSumActor.class), "printSum");

        //路由分发
        processRouter = this.getContext().actorOf(Props.create(ProcessContentActor.class, printSumActor)
                .withRouter(new RoundRobinPool(JavaNIOPoolMain.processPool)), "processRouter");
    }

    public void onReceive(Object message) {
        if (message instanceof Calculate) {
            //分解遍历
            for (int i = 0; i < buffer.capacity(); i = i + JavaNIOPoolMain.ForkSize) {
                processRouter.tell(new Event(i, i + JavaNIOPoolMain.ForkSize, buffer), getSelf());
            }
        } else {
            unhandled(message);
        }
    }
}
