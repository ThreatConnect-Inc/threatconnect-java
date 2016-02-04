/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.threatconnect.sdk.server.entity.format.DateSerializer;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author eric
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "FileOccurrence")
public class FileOccurrence
{
    @XmlElement(name = "Id", required = true)
    private Integer id;
    @XmlElement(name = "FileName", required = true)
    private String fileName;
    @XmlElement(name = "Path", required = true)
    private String path;
    @JsonSerialize(using = DateSerializer.class)
    @XmlElement(name = "Date", required = true)
    private Date date;
    
    public FileOccurrence()
    {
    }

    public FileOccurrence(Integer id, String fileName, String path, Date date)
    {
        this.id = id;
        this.fileName = fileName;
        this.path = path;
        this.date = date;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public boolean equals(Object object)
    {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        FileOccurrence that = (FileOccurrence) object;
        return java.util.Objects.equals(fileName, that.fileName) &&
                java.util.Objects.equals(path, that.path) &&
                java.util.Objects.equals(date, that.date);
    }

}
