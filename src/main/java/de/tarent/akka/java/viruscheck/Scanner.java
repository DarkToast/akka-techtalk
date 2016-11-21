package de.tarent.akka.java.viruscheck;

import de.tarent.akka.java.Resource;

class Scanner {
    public VirusScan scanForVirus(Resource resource) {
        if(resource.content.contains("VIRUS")) {
            return VirusScan.found(resource.name, "VIRUS");
        } else {
            return VirusScan.notFound(resource.name);
        }
    }
}
