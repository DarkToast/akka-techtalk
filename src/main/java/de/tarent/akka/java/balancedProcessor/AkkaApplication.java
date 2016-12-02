package de.tarent.akka.java.balancedProcessor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import de.tarent.akka.java.helloWorld.actors.Terminator;
import de.tarent.akka.java.balancedProcessor.actors.ResourceActor;

public class AkkaApplication {
    public static void main(String[] args) throws InterruptedException {
        ActorSystem system = ActorSystem.create("ScheduledBalancer");

        ActorRef resourceActor = system.actorOf(Props.create(ResourceActor.class), "resourceActor");
        system.actorOf(Props.create(Terminator.class, resourceActor), "terminator");
    }

}
