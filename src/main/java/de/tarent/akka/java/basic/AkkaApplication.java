package de.tarent.akka.java.basic;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import de.tarent.akka.java.basic.actors.HelloWorld;
import de.tarent.akka.java.basic.actors.Terminator;

public class AkkaApplication {
    public static void main(String[] args) throws InterruptedException {
        ActorSystem basic = ActorSystem.create("Basic");

        ActorRef helloWorld = basic.actorOf(Props.create(HelloWorld.class), "helloWorld");

        basic.actorOf(Props.create(Terminator.class, helloWorld), "terminator");
    }

}
