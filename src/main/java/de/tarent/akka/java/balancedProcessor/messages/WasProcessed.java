package de.tarent.akka.java.balancedProcessor.messages;

public class WasProcessed {
    public final String resource;

    public WasProcessed(String resource) {
        this.resource = resource;
    }
}
