package de.tarent.akka.java.remote.model;

public class WakeUp {
    public static boolean match(Object object) {
        return object instanceof WakeUp;
    }
}
