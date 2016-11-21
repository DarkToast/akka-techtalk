package de.tarent.akka.java;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import de.tarent.akka.java.checksum.CalculateChecksumMessage;
import de.tarent.akka.java.checksum.ChecksumActor;
import de.tarent.akka.java.viruscheck.CheckVirusMessage;
import de.tarent.akka.java.viruscheck.VirusScanActor;

public class AkkaApplication {

    public static void main(String[] args) throws InterruptedException {
        new AkkaApplication().start();
    }

    private void start() throws InterruptedException {
        ActorSystem actorSystem = ActorSystem.create("Techtalk");
        ActorRef checksumActor = actorSystem.actorOf(ChecksumActor.configure());
        ActorRef virusCheckActor = actorSystem.actorOf(VirusScanActor.configure());

        Resource resource = new Resource("./foobar.txt", "MY CONTENT");
        checksumActor.tell(new CalculateChecksumMessage(resource), null);
        virusCheckActor.tell(new CheckVirusMessage(resource), null);

        System.out.println("I am still able to perform stuff.");

        Thread.sleep(10000);
        actorSystem.shutdown();
    }


}
