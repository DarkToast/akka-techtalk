package de.tarent.akka.java.supervision.actors;

import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import de.tarent.akka.java.supervision.messages.Divide;
import de.tarent.akka.java.supervision.messages.Result;

class DivisionActor extends UntypedActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public static Props props() {
        return Props.create(DivisionActor.class);
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if(message instanceof Divide) {
            Divide divide = (Divide) message;

            double result = divide.x / divide.y;
            log.info("Division was processed: {} / {} = {}", divide.x, divide.y, result);
            sender().tell(new Result(result), self());
        } else {
            unhandled(message);
        }
    }
}
