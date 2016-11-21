package de.tarent.akka.java.fullExample.viruscheck;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import de.tarent.akka.java.fullExample.Resource;
import de.tarent.akka.java.fullExample.store.ProcessedResource;
import de.tarent.akka.java.fullExample.store.StoreMessage;

import java.util.Random;

public class VirusScanActor extends UntypedActor {
    private final Scanner scanner;
    private final ActorRef storeActor;

    public VirusScanActor(Scanner scanner, ActorRef storeActor) {
        this.scanner = scanner;
        this.storeActor = storeActor;
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        Thread.sleep(new Random().nextInt(5000));

        if(CheckVirusMessage.match(message)) {
            final Resource resource = ((CheckVirusMessage) message).resource;
            VirusScan scan = scanner.scanForVirus(resource);
            System.out.println("Scan was successful: " + scan.found);

            sendToStore(scan);
        } else {
            unhandled(message);
        }
    }

    private void sendToStore(VirusScan scan) {
        StoreMessage message = new StoreMessage(ProcessedResource.fromVirusScan(scan));
        storeActor.tell(message, self());
    }

    public static Props configure(ActorRef storeActor) {
        return Props.create(VirusScanActor.class, new Scanner(), storeActor);
    }
}
