/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.entity;

import com.cyber2.api.lib.server.entity.format.DateSerializer;
import com.cyber2.api.lib.server.entity.init.ApiEntityInit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.codehaus.jackson.map.annotate.JsonSerialize;

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
    
    public SecurityLabel()
    {
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
    
}
