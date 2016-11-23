package de.tarent.akka.java.basic.actors;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import de.tarent.akka.java.basic.messages.ReceiveMessage;
import de.tarent.akka.java.basic.messages.SendMessage;

public class FirstActor extends UntypedActor {
    private final ActorRef secondActor;

    public FirstActor(ActorRef secondActor) {
        this.secondActor = secondActor;
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if(message instanceof SendMessage) {
            System.out.println("Receiving message and sending it to actor two: " + ((SendMessage) message).content);
            secondActor.tell(message, self());

        } else if(message instanceof ReceiveMessage) {
            System.out.println("Received message : " + ((ReceiveMessage) message).content);

        } else {
            unhandled(message);
        }
    }
}
