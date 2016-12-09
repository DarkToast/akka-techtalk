package de.tarent.akka.java.helloWorld;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import de.tarent.akka.java.helloWorld.actors.HelloWorldActor;
import de.tarent.akka.java.helloWorld.actors.Terminator;

public class AkkaApplication {
    public static void main(String[] args) throws InterruptedException {

        // Creating an actor system
        final ActorSystem basic = ActorSystem.create("Basic");

        // `Props` are like factory recipes of Actor classes.
        final Props actorCreationRecipe = Props.create(HelloWorldActor.class);

        // Creating an `ActorRef` by a `Props`.
        final ActorRef helloWorld = basic.actorOf(actorCreationRecipe, "helloWorld");

        // Creating a second `ActorRef` of the `Terminator` actor..
        basic.actorOf(Props.create(Terminator.class, helloWorld), "terminator");
    }

}
