package de.tarent.akka.java.balancedProcessor.actors;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import de.tarent.akka.java.balancedProcessor.messages.ProcessResource;
import de.tarent.akka.java.balancedProcessor.messages.ResourceProcessed;

class ProcessorActor extends UntypedActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void onReceive(Object message) throws Throwable {
        if(message instanceof ProcessResource) {
            final String resource = ((ProcessResource) message).resource;
            log.info("Receiving a process resource message with content {}", resource);
            Thread.sleep(2000);

            sender().tell(new ResourceProcessed(resource), self());
        }
    }
}
