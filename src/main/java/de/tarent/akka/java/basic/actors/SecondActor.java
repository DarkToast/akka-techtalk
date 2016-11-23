package de.tarent.akka.java.basic.actors;

import akka.actor.UntypedActor;
import de.tarent.akka.java.basic.messages.ReceiveMessage;
import de.tarent.akka.java.basic.messages.SendMessage;

public class SecondActor extends UntypedActor {
    @Override
    public void onReceive(Object message) throws Throwable {
        if(message instanceof SendMessage) {
            System.out.println("Receiving message from actor one. Sending back: " + ((SendMessage) message).content);

            ReceiveMessage receiveMessage = new ReceiveMessage(((SendMessage) message).content);

            // sender() points to our calling actor.
            sender().tell(receiveMessage, self());

        } else {
            unhandled(message);
        }
    }
}
