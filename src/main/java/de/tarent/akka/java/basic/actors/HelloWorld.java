package de.tarent.akka.java.basic.actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import de.tarent.akka.java.basic.messages.HelloMessage;
import de.tarent.akka.java.basic.messages.GreetMessage;

public class HelloWorld extends UntypedActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    private final ActorRef greeter;

    public HelloWorld() {
        greeter = getContext().actorOf(Props.create(Greeter.class), "greeter");
    }

    @Override
    public void preStart() throws Exception {
        greeter.tell(new HelloMessage("Hello World!"), self());
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if(message instanceof GreetMessage) {
            log.info("Receiving greeting " + ((GreetMessage) message).content);
            getContext().stop(self());
        } else {
            unhandled(message);
        }
    }
}
