package de.tarent.akka.java.balancedProcessor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import de.tarent.akka.java.helloWorld.actors.Terminator;
import de.tarent.akka.java.balancedProcessor.actors.ProducerActor;

public class AkkaApplication {
    public static void main(String[] args) throws InterruptedException {

        // The system
        final ActorSystem system = ActorSystem.create("ScheduledBalancer");

        // The resource producer
        final ActorRef resourceActor = system.actorOf(Props.create(ProducerActor.class), "producerActor");


        system.actorOf(Props.create(Terminator.class, resourceActor), "terminator");
    }

}
