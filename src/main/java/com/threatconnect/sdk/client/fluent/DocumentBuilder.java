package com.threatconnect.sdk.client.fluent;

import com.threatconnect.sdk.server.entity.Document;
import com.threatconnect.sdk.server.entity.Owner;

import java.util.Date;

public class DocumentBuilder
{
    private Integer id;
    private String name;
    private String type;
    private Owner owner;
    private String ownerName;
    private Date dateAdded;
    private String webLink;
    private String fileName;
    private Long fileSize;
    private String status;

    public DocumentBuilder withId(Integer id)
    {
        this.id = id;
        return this;
    }

    public DocumentBuilder withName(String name)
    {
        this.name = name;
        return this;
    }

    public DocumentBuilder withType(String type)
    {
        this.type = type;
        return this;
    }

    public DocumentBuilder withOwner(Owner owner)
    {
        this.owner = owner;
        return this;
    }

    public DocumentBuilder withOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
        return this;
    }

    public DocumentBuilder withDateAdded(Date dateAdded)
    {
        this.dateAdded = dateAdded;
        return this;
    }

    public DocumentBuilder withWebLink(String webLink)
    {
        this.webLink = webLink;
        return this;
    }

    public DocumentBuilder withFileName(String fileName)
    {
        this.fileName = fileName;
        return this;
    }

    public DocumentBuilder withFileSize(Long fileSize)
    {
        this.fileSize = fileSize;
        return this;
    }

    public DocumentBuilder withStatus(String status)
    {
        this.status = status;
        return this;
    }

    public Document createDocument()
    {
        return new Document(id, name, type, owner, ownerName, dateAdded, webLink, fileName, fileSize, status);
    }
}