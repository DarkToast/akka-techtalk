package de.tarent.akka.java.fullExample.checksum;

import de.tarent.akka.java.fullExample.Resource;

public class CalculateChecksumMessage {
    public static boolean match(Object object) {
        return object instanceof CalculateChecksumMessage;
    }

    public final Resource resource;
    public CalculateChecksumMessage(Resource resource) {
        this.resource = resource;
    }
}
