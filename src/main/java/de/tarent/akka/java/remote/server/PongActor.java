package de.tarent.akka.java.remote.server;

import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import de.tarent.akka.java.remote.model.Ping;
import de.tarent.akka.java.remote.model.Pong;

class PongActor extends UntypedActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void onReceive(Object message) throws Throwable {
        if(Ping.match(message)) {
            log.info("Receiving a ping. Will pong back.");
            sender().tell(new Pong(), self());
        } else {
            unhandled(message);
        }

        Thread.sleep(500);
    }

    public static Props props() {
        return Props.create(PongActor.class);
    }
}
