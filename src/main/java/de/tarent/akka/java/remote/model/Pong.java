package de.tarent.akka.java.remote.model;

import java.io.Serializable;

public class Pong implements Serializable {
    public static boolean match(Object object) {
        return object instanceof Pong;
    }
}
