/**
 * 
 */
package com.cjy.code.actor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * 类ActorTest.java的实现描述：TODO 类实现描述.
 * 
 * @author Administrator 2015年12月28日 上午10:28:36
 */
public class ActorTest {

    /**
     * 
     */
    private static final Logger LOG            = LoggerFactory.getLogger(ActorTest.class);

    private static final String ACTOR_REF_NAME = "parent-actor";

    private static final long   TIME_SLEEP     = 5000l;

    /**
     * "Exception".
     * 
     * @param args "dsds"
     * @throws Exception "dsds"
     */
    public static void main(String... args) throws Exception {

        final ActorSystem actorSystem = ActorSystem.create("actor-system");

        Thread.sleep(TIME_SLEEP);

        final ActorRef actorRef = actorSystem.actorOf(Props.create(ParentActor.class), ACTOR_REF_NAME);

        actorRef.tell(new Command("CMD 1"), null);
        actorRef.tell(new Command("CMD 2"), null);
        actorRef.tell(new Command("CMD 3"), null);
        actorRef.tell("echo", null);
        actorRef.tell(new Command("CMD 4"), null);
        actorRef.tell(new Command("CMD 5"), null);

        Thread.sleep(TIME_SLEEP);

        LOG.debug("Actor System Shutdown Starting...");

        actorSystem.shutdown();
    }

}
