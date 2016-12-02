package de.tarent.akka.java.supervision.actors;

import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import de.tarent.akka.java.supervision.messages.Plus;
import de.tarent.akka.java.supervision.messages.Result;

class SummationActor extends UntypedActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void onReceive(Object message) throws Throwable {
        if(message instanceof Plus) {
            Plus plus = (Plus) message;

            int result = plus.x + plus.y;
            log.info("Summation was processed: {} + {} = {}", plus.x, plus.y, result);
            sender().tell(new Result(result), self());
        } else {
            unhandled(message);
        }
    }

    public static Props props() {
        return Props.create(SummationActor.class);
    }
}
