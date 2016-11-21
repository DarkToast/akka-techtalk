package de.tarent.akka.java.fullExample.store;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class ResourceStore {
    final Map<String, ProcessedResource> store;

    ResourceStore() {
        this.store = new HashMap<>();
    }

    public void storeAndMerge(ProcessedResource resource) {
        Optional<ProcessedResource> optionalResource = get(resource.getName());

        if(!optionalResource.isPresent()) {
            store.put(resource.getName(), resource);
        } else {
            store.put(resource.getName(), merge(resource, optionalResource.get()));
        }
    }

    private ProcessedResource merge(ProcessedResource first, ProcessedResource second) {
        if(first.getChecksum() != null) { second.setChecksum(first.getChecksum()); }
        if(first.getContent() != null) { second.setContent(first.getContent()); }
        if(first.containsVirus() != null) { second.setContainsVirus(first.containsVirus()); }

        return second;
    }

    public Optional<ProcessedResource> get(final String resourceName) {
        return Optional.ofNullable(store.get(resourceName));
    }
}