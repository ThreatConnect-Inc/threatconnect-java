/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.threatconnect.sdk.server.entity.format.DateSerializer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 *
 * @author Cole Iliff
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "SecurityLabel")
public class SecurityLabel
{
    @XmlElement(name = "Name", required = true)
    private String name;
    @XmlElement(name = "Description", required = false)
    private String description;
    @JsonSerialize(using = DateSerializer.class)
    @XmlElement(name = "DateAdded", required = false)
    private Date dateAdded;
    @XmlElement(name = "Color", required = false)
    private String color;
    
    public SecurityLabel()
    {
    }

    public SecurityLabel(String name, String description, Date dateAdded)
    {
        this.name = name;
        this.description = description;
        this.dateAdded = dateAdded;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Date getDateAdded()
    {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded)
    {
        this.dateAdded = dateAdded;
    }
    
    public String getColor()
    {
        return color;
    }
    
    public void setColor(final String color)
    {
        this.color = color;
    }
}
