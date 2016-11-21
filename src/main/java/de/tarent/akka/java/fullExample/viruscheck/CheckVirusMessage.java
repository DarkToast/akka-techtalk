package de.tarent.akka.java.fullExample.viruscheck;

import de.tarent.akka.java.fullExample.Resource;

public class CheckVirusMessage {
    public static boolean match(Object object) {
        return object instanceof CheckVirusMessage;
    }

    public final Resource resource;
    public CheckVirusMessage(Resource resource) {
        this.resource = resource;
    }
}
