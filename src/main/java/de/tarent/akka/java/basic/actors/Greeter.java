package de.tarent.akka.java.basic.actors;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import de.tarent.akka.java.basic.messages.HelloMessage;
import de.tarent.akka.java.basic.messages.GreetMessage;

class Greeter extends UntypedActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void onReceive(Object message) throws Throwable {
        if(message instanceof HelloMessage) {
            log.info("Receiving a hello: " + ((HelloMessage) message).content);

            // sender() points to our calling actor. In this case it is the `ActorRef`
            // of the `HelloWorld` actor.
            sender().tell(new GreetMessage("Hello Universe!"), self());
        } else {
            unhandled(message);
        }
    }
}
