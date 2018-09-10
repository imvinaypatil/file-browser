package com.vin8.filebrowser.files.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Directory {

    @Id
    private final String id;
    private String name;
    private String parentDirectory;
    private final String owner;
    private final Long creationTime;

    public Directory(String id, String name, String parentDirectory, String owner, Long creationTime) {
        this.id = id;
        this.name = name;
        this.parentDirectory = parentDirectory;
        this.owner = owner;
        this.creationTime = creationTime;
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
}
