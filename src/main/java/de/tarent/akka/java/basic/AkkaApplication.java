package de.tarent.akka.java.basic;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import de.tarent.akka.java.basic.actors.HelloWorld;
import de.tarent.akka.java.basic.actors.Terminator;

public class AkkaApplication {
    public static void main(String[] args) throws InterruptedException {

        // Creating an actor system
        ActorSystem basic = ActorSystem.create("Basic");

        // Creating an `ActorRef` by a `Props`.
        // `Props` are like factory recipes of Actor classes.
        ActorRef helloWorld = basic.actorOf(Props.create(HelloWorld.class), "helloWorld");

        // Creating a second `ActorRef` of the `Terminator` actor..
        basic.actorOf(Props.create(Terminator.class, helloWorld), "terminator");
    }

}
