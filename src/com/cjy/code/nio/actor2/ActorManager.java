package com.cjy.code.nio.actor2;

import java.nio.ByteBuffer;

import com.cjy.code.nio.JavaNIOPoolMain;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.RoundRobinPool;

public class ActorManager {

    public void process(ByteBuffer buffer) {

        ActorSystem system = ActorSystem.create("ActorSystem");

        ActorRef master = system.actorOf(Props.create(ActorProcess.class, buffer), "master");

        master.tell(new Calculate(), master);

    }

    public static class ActorProcess extends UntypedActor {
        private final ByteBuffer buffer;

        private final ActorRef   processRouter;

        private int              sum = 0;

        public ActorProcess(ByteBuffer buffer) {
            this.buffer = buffer;

            processRouter = this.getContext().actorOf(Props.create(ProcessContent.class)
                    .withRouter(new RoundRobinPool(Runtime.getRuntime().availableProcessors() * 2)), "processRouter");
        }

        public void onReceive(Object message) {
            if (message instanceof Calculate) {
                for (int i = 0; i < buffer.capacity(); i = i + JavaNIOPoolMain.ForkSize) {
                    final int begin = i;
                    processRouter.tell(new Event(begin, begin + JavaNIOPoolMain.ForkSize, buffer), getSelf());
                }

            } else if (message instanceof Result) {
                Result result = (Result) message;

                sum = sum + result.getValue();

                System.out.println("sum:" + sum);
            } else {
                unhandled(message);
            }
        }
    }

    static class Result {
        private final int value;

        public Result(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public static class ProcessContent extends UntypedActor {

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

        public void onReceive(Object message) {
            if (message instanceof Event) {
                Event event = (Event) message;
                int result = getContent(event.getBegin(), event.getEnd(), event.getBuffer());
                getSender().tell(new Result(result), getSelf());
            } else {
                unhandled(message);
            }
        }
    }

    static class Calculate {
    }

}
