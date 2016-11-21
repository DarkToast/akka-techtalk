package de.tarent.akka.java.store;

public class ProcessedResource {
    private String name;
    private String content;
    private String checksum;
    private Boolean containsVirus;

    public ProcessedResource(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    public Boolean containsVirus() {
        return containsVirus;
    }

    public void setContainsVirus(boolean containsVirus) {
        this.containsVirus = containsVirus;
    }

    @Override
    public String toString() {
        return "ProcessedResource{" +
                "name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", checksum='" + checksum + '\'' +
                ", containsVirus=" + containsVirus +
                '}';
    }
}
