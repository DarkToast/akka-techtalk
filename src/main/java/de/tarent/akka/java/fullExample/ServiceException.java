package de.tarent.akka.java.fullExample;

public class ServiceException extends RuntimeException {
    public ServiceException(Throwable cause) {
        super(cause);
    }
}
