package com.threatconnect.sdk.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created by cblades on 4/20/2015.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Document")
public class Document extends Group
{
    @XmlElement(name = "fileName", required = false)
    private String fileName;
    @XmlElement(name = "fileSize", required = false)
    private Long fileSize;
    @XmlElement(name = "status", required = false)
    private String status;

    public Document() {
    }

    public Document(Integer id, String name, String type, Owner owner, String ownerName, Date dateAdded, String webLink, String fileName, Long fileSize, String status)
    {
        super(id, name, type, owner, ownerName, dateAdded, webLink);
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.status = status;
    }

    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public Long getFileSize()
    {
        return fileSize;
    }

    public void setFileSize(Long fileSize)
    {
        this.fileSize = fileSize;
    }

    /**
     * Ignore serialization of status because status isn't settable by API call.
     * @return
     */
    @JsonIgnore
    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }
}

