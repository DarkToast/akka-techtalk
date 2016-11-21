package de.tarent.akka.java.fullExample.viruscheck;

import de.tarent.akka.java.fullExample.Resource;

class Scanner {
    public VirusScan scanForVirus(Resource resource) {
        if(resource.content.toUpperCase().contains("VIRUS")) {
            return VirusScan.found(resource.name, "VIRUS");
        } else {
            return VirusScan.notFound(resource.name);
        }
    }
}
