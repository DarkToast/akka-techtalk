package de.tarent.akka.java.remoteAroundTheWorld.model;

public class WakeUp {
    public static boolean match(Object object) {
        return object instanceof WakeUp;
    }
}
