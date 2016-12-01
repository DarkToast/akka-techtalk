package de.tarent.akka.java.remote.model;

import java.io.Serializable;

public class Ping implements Serializable {
    public static boolean match(Object object) {
        return object instanceof Ping;
    }

}
