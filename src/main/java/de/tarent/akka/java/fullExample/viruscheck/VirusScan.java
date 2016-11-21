package de.tarent.akka.java.fullExample.viruscheck;

import java.util.Optional;

public class VirusScan {
    public final String resouceName;
    public final Optional<String> virusName;
    public final boolean found;

    private VirusScan(String resouceName, String virusName, boolean found) {
        this.resouceName = resouceName;
        this.virusName = Optional.ofNullable(virusName);
        this.found = found;
    }

    static VirusScan found(String resouceName, String virusName) {
        return new VirusScan(resouceName, virusName, true);
    }

    static VirusScan notFound(String resouceName) {
        return new VirusScan(resouceName, null, false);
    }
}
