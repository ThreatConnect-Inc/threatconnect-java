package com.threatconnect.sdk.client.fluent;

import com.threatconnect.sdk.server.entity.Owner;
import com.threatconnect.sdk.server.entity.Signature;

import java.util.Date;

public class SignatureBuilder
{
    private Integer id;
    private String name;
    private String type;
    private Owner owner;
    private String ownerName;
    private Date dateAdded;
    private String webLink;
    private String fileType;
    private String fileName;
    private String fileText;

    public SignatureBuilder withId(Integer id)
    {
        this.id = id;
        return this;
    }

    public SignatureBuilder withName(String name)
    {
        this.name = name;
        return this;
    }

    public SignatureBuilder withType(String type)
    {
        this.type = type;
        return this;
    }

    public SignatureBuilder withOwner(Owner owner)
    {
        this.owner = owner;
        return this;
    }

    public SignatureBuilder withOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
        return this;
    }

    public SignatureBuilder withDateAdded(Date dateAdded)
    {
        this.dateAdded = dateAdded;
        return this;
    }

    public SignatureBuilder withWebLink(String webLink)
    {
        this.webLink = webLink;
        return this;
    }

    public SignatureBuilder withFileType(String fileType)
    {
        this.fileType = fileType;
        return this;
    }

    public SignatureBuilder withFileName(String fileName)
    {
        this.fileName = fileName;
        return this;
    }

    public SignatureBuilder withFileText(String fileText)
    {
        this.fileText = fileText;
        return this;
    }

    public Signature createSignature()
    {
        return new Signature(id, name, type, owner, ownerName, dateAdded, webLink, fileType, fileName, fileText);
    }
}