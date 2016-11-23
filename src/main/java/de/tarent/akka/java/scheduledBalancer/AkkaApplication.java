package de.tarent.akka.java.scheduledBalancer;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import de.tarent.akka.java.basic.actors.Terminator;
import de.tarent.akka.java.scheduledBalancer.actors.ResourceActor;
import de.tarent.akka.java.scheduledBalancer.messages.NextTick;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

public class AkkaApplication {
    public static void main(String[] args) throws InterruptedException {
        ActorSystem system = ActorSystem.create("ScheduledBalancer");

        ActorRef resourceActor = system.actorOf(Props.create(ResourceActor.class), "resourceActor");
        system.actorOf(Props.create(Terminator.class, resourceActor), "terminator");
    }

}
