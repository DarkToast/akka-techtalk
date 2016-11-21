package de.tarent.akka.java.fullExample.checksum;

import de.tarent.akka.java.fullExample.Resource;

import java.util.Base64;

class Calculator {
    Checksum calculate(Resource resource) {
        final String checksum = Base64.getEncoder().encodeToString(
            resource.content.getBytes()
        );
        return new Checksum(resource.name, checksum.substring(1, 10));
    }
}
