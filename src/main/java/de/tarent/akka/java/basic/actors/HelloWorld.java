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
        // The `HelloWorld` actor creates an child `ActorRef` of the `Greeter`
        // actor.
        greeter = getContext().actorOf(Props.create(Greeter.class), "greeter");
    }

    /**
     * When this actor starts, it sends a `HelloWorld` message to
     * its child actor.
     *
     * @throws Exception
     */
    @Override
    public void preStart() throws Exception {
        // Sending a `HelloWorld` to the `Greeter`
        // The self() will send the actorRef of this actor to the `Greeter`.
        greeter.tell(new HelloMessage("Hello World!"), self());
    }

    /**
     * The main method of the akka actor, that receives messages.
     *
     * @param message
     * @throws Throwable
     */
    @Override
    public void onReceive(Object message) throws Throwable {
        if(message instanceof GreetMessage) {
            log.info("Receiving greeting " + ((GreetMessage) message).content);

            // Stops itself (Will send a `Terminated` message to the `Terminator` actor
            getContext().stop(self());
        } else {
            unhandled(message);
        }
    }
}
