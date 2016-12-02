package de.tarent.akka.java.remoteAroundTheWorld.client;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import de.tarent.akka.java.remoteAroundTheWorld.model.Ping;
import de.tarent.akka.java.remoteAroundTheWorld.model.Pong;
import de.tarent.akka.java.remoteAroundTheWorld.model.WakeUp;

class PingActor extends UntypedActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private ActorRef pongActor;

    public PingActor(ActorRef pongActor) {
        this.pongActor = pongActor;
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if(Pong.match(message)) {
            log.info("Receiving a pong!");

        } else if(WakeUp.match(message)) {
            log.info("Ou what!? ... Sending a ping!");
            pongActor.tell(new Ping(), self());

        } else {
            unhandled(message);
        }
    }

    public static Props props(ActorRef pongActor) {
        return Props.create(PingActor.class, pongActor);
    }
}
