package de.tarent.akka.java.checksum;

public class Checksum {
    public final String resourceName;
    public final String base64Checksum;

    public Checksum(String resourceName, String base64Checksum) {
        this.resourceName = resourceName;
        this.base64Checksum = base64Checksum;
    }
}
