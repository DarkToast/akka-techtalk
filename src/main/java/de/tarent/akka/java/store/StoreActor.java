package de.tarent.akka.java.store;

import akka.actor.Props;
import akka.actor.UntypedActor;
import de.tarent.akka.java.viruscheck.VirusScanActor;

public class StoreActor extends UntypedActor {

    private final ResourceStore resourceStore;

    public StoreActor(ResourceStore resourceStore) {
        this.resourceStore = resourceStore;
    }


    @Override
    public void onReceive(Object message) throws Throwable {

    }

    public static Props configure() {
        return Props.create(VirusScanActor.class, new ResourceStore());
    }
}
