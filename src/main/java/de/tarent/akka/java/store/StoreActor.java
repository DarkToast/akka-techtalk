package de.tarent.akka.java.store;

import akka.actor.Props;
import akka.actor.UntypedActor;

public class StoreActor extends UntypedActor {

    private final ResourceStore resourceStore;

    public StoreActor(ResourceStore resourceStore) {
        this.resourceStore = resourceStore;
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if(StoreMessage.match(message)) {
            ProcessedResource resource = ((StoreMessage) message).processedResource;
            resourceStore.storeAndMerge(resource);
            System.out.println("Storing resource: " + resource);
        } else {
            unhandled(message);
        }
    }

    public static Props configure() {
        return Props.create(StoreActor.class, new ResourceStore());
    }
}
