package com.vin8.filebrowser.files;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@org.springframework.data.mongodb.core.mapping.Document
public class Document {

    @Id
    final private String id;
    private String name;
    private String parentDirectory;
    private final String owner;
    private final Long creationTime;
    private final String type;

    public Document(String id, String name, String parentDirectory, String owner, Long creationTime, String type) {
        this.id = id;
        this.name = name;
        this.parentDirectory = parentDirectory;
        this.owner = owner;
        this.creationTime = creationTime;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getParentDirectory() {
        return parentDirectory;
    }

    public String getOwner() {
        return owner;
    }

    public Long getCreationTime() {
        return creationTime;
    }

    public String getType() {
        return type;
    }
}
