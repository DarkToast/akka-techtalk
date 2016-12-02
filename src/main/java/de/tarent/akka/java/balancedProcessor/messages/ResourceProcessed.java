package de.tarent.akka.java.balancedProcessor.messages;

public class ResourceProcessed {
    public final String resource;

    public ResourceProcessed(String resource) {
        this.resource = resource;
    }
}
