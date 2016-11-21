package de.tarent.akka.java.checksum;

import akka.actor.Props;
import akka.actor.UntypedActor;
import de.tarent.akka.java.Resource;

import java.util.Random;

public class ChecksumActor extends UntypedActor {

    private final Calculator calculator;

    public ChecksumActor(Calculator calculator) {
        this.calculator = calculator;
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        Thread.sleep(new Random().nextInt(5000));

        if (CalculateChecksumMessage.match(message)) {
            Resource resource = ((CalculateChecksumMessage) message).resource;
            Checksum checksum = calculator.calculate(resource);

            System.out.println("Checksum is: " + checksum.base64Checksum);
        } else {
            unhandled(message);
        }
    }

    public static Props configure() {
        return Props.create(ChecksumActor.class, new Calculator());
    }
}
