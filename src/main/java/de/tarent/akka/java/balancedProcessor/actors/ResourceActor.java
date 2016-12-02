package de.tarent.akka.java.balancedProcessor.actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.routing.RoundRobinPool;
import de.tarent.akka.java.balancedProcessor.messages.ProcessResource;
import de.tarent.akka.java.balancedProcessor.messages.ResourceProcessed;

import java.util.concurrent.atomic.AtomicInteger;

public class ResourceActor extends UntypedActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private final ActorRef processor;
    private final AtomicInteger msgCount = new AtomicInteger(0);

    public ResourceActor() {
        // Create a `Props` object, that will build us a `RoundRobinPool`
        // with 5 `ProcessorActor` actors.
        Props props = new RoundRobinPool(5).props(Props.create(ProcessorActor.class));

        // This `ActorRef` now points to the `RoundRobinPool`.
        this.processor = getContext().actorOf(props);
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof ResourceProcessed) {
            ResourceProcessed processed = (ResourceProcessed) message;

            log.info("Resource was processed with content {}", processed.resource);
            int count = msgCount.incrementAndGet();

            if (count == 20) {
                getContext().stop(self());
            }
        } else {
            unhandled(message);
        }
    }

    @Override
    public void preStart() throws Exception {
        for(int i = 0; i < 20; i++) {
            ProcessResource processResource = new ProcessResource("Hallo Welt " + i);
            // Every message will balanced between the 5 `ProcessorActor` actors.
            processor.tell(processResource, self());
        }
    }

}
