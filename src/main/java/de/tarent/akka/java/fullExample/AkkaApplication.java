package de.tarent.akka.java.fullExample;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import de.tarent.akka.java.fullExample.checksum.CalculateChecksumMessage;
import de.tarent.akka.java.fullExample.checksum.ChecksumActor;
import de.tarent.akka.java.fullExample.store.ProcessedResource;
import de.tarent.akka.java.fullExample.store.StoreActor;
import de.tarent.akka.java.fullExample.store.StoreMessage;
import de.tarent.akka.java.fullExample.viruscheck.CheckVirusMessage;
import de.tarent.akka.java.fullExample.viruscheck.VirusScanActor;

public class AkkaApplication {

    public static void main(String[] args) throws InterruptedException {
        new AkkaApplication().start();
    }

    private void start() throws InterruptedException {
        ActorSystem actorSystem = ActorSystem.create("Techtalk");

        ActorRef storeActor = actorSystem.actorOf(StoreActor.configure());
        ActorRef checksumActor = actorSystem.actorOf(ChecksumActor.configure(storeActor));
        ActorRef virusCheckActor = actorSystem.actorOf(VirusScanActor.configure(storeActor));

        // -- Sending

        // First a resource
        Resource resource = new Resource("./foobar.txt", "MY Virus CONTENT");

        // Sending to the checksum actor
        checksumActor.tell(new CalculateChecksumMessage(resource), null);

        // Sending to the virus scan actor
        virusCheckActor.tell(new CheckVirusMessage(resource), null);

        // Sendting to the store Actor
        storeActor.tell(new StoreMessage(ProcessedResource.fromResource(resource)), null);

        System.out.println("I am still able to perform stuff.");

        Thread.sleep(10000);
        actorSystem.shutdown();
    }
}
