package de.tarent.akka.java.fullExample.store;

public class StoreMessage {
    public final ProcessedResource processedResource;

    public StoreMessage(ProcessedResource processedResource) {
        this.processedResource = processedResource;
    }

    public static boolean match(Object object) {
        return object instanceof StoreMessage;
    }
}
