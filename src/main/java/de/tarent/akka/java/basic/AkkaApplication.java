package de.tarent.akka.java.basic;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import de.tarent.akka.java.basic.actors.FirstActor;
import de.tarent.akka.java.basic.actors.SecondActor;
import de.tarent.akka.java.basic.messages.SendMessage;

public class AkkaApplication {
    public static void main(String[] args) throws InterruptedException {
        ActorSystem basic = ActorSystem.create("Basic");

        ActorRef secondActorRef = basic.actorOf(
            Props.create(SecondActor.class), "secondActor"
        );

        ActorRef firstActorRef = basic.actorOf(
            Props.create(FirstActor.class, secondActorRef), "firstActor"
        );

        SendMessage message = new SendMessage("Hallo Welt!");
        firstActorRef.tell(message, null);

        Thread.sleep(1000);
        basic.shutdown();
    }
}
