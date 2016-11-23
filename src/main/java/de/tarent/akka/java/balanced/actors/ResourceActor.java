package de.tarent.akka.java.balanced.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.FI;
import akka.japi.pf.ReceiveBuilder;
import akka.routing.RoundRobinPool;
import de.tarent.akka.java.balanced.messages.ProcessResource;
import de.tarent.akka.java.balanced.messages.ResourceProcessed;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

import java.util.concurrent.atomic.AtomicInteger;

public class ResourceActor extends AbstractActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private final ActorRef processor;
    private final AtomicInteger msgCount = new AtomicInteger(0);

    public ResourceActor() {
        Props props = new RoundRobinPool(5).props(Props.create(ProcessorActor.class));
        this.processor = getContext().actorOf(props);

        configReceive();
    }

    @Override
    public void preStart() throws Exception {
        for(int i = 0; i < 50; i++) {
           ProcessResource processResource = new ProcessResource("Hallo Welt " + i);
            processor.tell(processResource, self());
        }
    }

    private void configReceive() {
        PartialFunction<Object, BoxedUnit> receiveFunction = ReceiveBuilder
                .match(ResourceProcessed.class, processed())
                .matchAny(o -> log.info("received unknown message {}", o))
                .build();

        receive(receiveFunction);
    }

    private FI.UnitApply<ResourceProcessed> processed() {
        return (processed) -> {
            log.info("Resource was processed with content {}", processed.resource);
            int count = msgCount.incrementAndGet();

            if(count == 50) {
                getContext().stop(self());
            }
        };
    }
}
