package de.tarent.akka.java.viruscheck;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import de.tarent.akka.java.checksum.ChecksumActor;
import de.tarent.akka.java.Resource;

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
        } else {
            unhandled(message);
        }
    }


    public static Props configure(ActorRef storeActor) {
        return Props.create(VirusScanActor.class, new Scanner(), storeActor);
    }
}
