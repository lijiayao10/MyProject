package com.cjy.code.nio.actor;

import java.nio.ByteBuffer;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class ActorManager {

    public void process(ByteBuffer buffer) {

        ActorSystem system = ActorSystem.create("ActorSystem");

        //·Ö·¢Actor
        ActorRef master = system.actorOf(Props.create(ActorProcess.class, buffer), "master");

        master.tell(new Calculate(), master);

    }
}
