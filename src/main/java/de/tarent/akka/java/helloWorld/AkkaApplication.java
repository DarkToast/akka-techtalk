package de.tarent.akka.java.helloWorld;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import de.tarent.akka.java.helloWorld.actors.HelloWorldActor;
import de.tarent.akka.java.helloWorld.actors.Terminator;

public class AkkaApplication {
    public static void main(String[] args) throws InterruptedException {

        // Creating an actor system
        ActorSystem basic = ActorSystem.create("Basic");

        // Creating an `ActorRef` by a `Props`.
        // `Props` are like factory recipes of Actor classes.
        ActorRef helloWorld = basic.actorOf(Props.create(HelloWorldActor.class), "helloWorld");

        // Creating a second `ActorRef` of the `Terminator` actor..
        basic.actorOf(Props.create(Terminator.class, helloWorld), "terminator");
    }

}
