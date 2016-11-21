package de.tarent.akka.java.store;

import de.tarent.akka.java.viruscheck.CheckVirusMessage;

public class StoreMessage {
    public final ProcessedResource processedResource;

    public StoreMessage(ProcessedResource processedResource) {
        this.processedResource = processedResource;
    }

    public static boolean match(Object object) {
        return object instanceof StoreMessage;
    }
}
