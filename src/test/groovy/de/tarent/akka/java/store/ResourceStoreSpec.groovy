package de.tarent.akka.java.store

import spock.lang.Specification
import spock.lang.Subject

class ResourceStoreSpec extends Specification {

    @Subject
    ResourceStore resourceStore = new ResourceStore()

    def "A resource can be persisted"() {
        given: "A ProcessedResource"
        ProcessedResource processedResource = new ProcessedResource("#name")

        when: "We persist a resource"
        resourceStore.storeAndMerge(processedResource)

        then: "We can receive the resource by its name"
        resourceStore.get("#name").isPresent()
        resourceStore.get("#name").get() == processedResource
    }

    def "A resource can be merged"() {
        given: "A ProcessedResource with a checksum"
        ProcessedResource processedResource = new ProcessedResource("#name2")
        processedResource.setChecksum("#checksum")

        and: "A second resource"
        ProcessedResource secondResource = new ProcessedResource("#name2")
        secondResource.setContent("#content")
        secondResource.setContainsVirus(true)

        when: "We persist the first resource"
        resourceStore.storeAndMerge(processedResource)

        and: "Persist the second resource"
        resourceStore.storeAndMerge(secondResource)

        then: "We can receive the resource by its name"
        resourceStore.get("#name2").isPresent()
        ProcessedResource actual = resourceStore.get("#name2").get()

        and: "The resource has all fields from the first resources"
        actual.getName() == "#name2"
        actual.getContent() == "#content"
        actual.getChecksum() == "#checksum"
        actual.containsVirus()
    }
}
