/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.entity;

import com.cyber2.api.lib.server.entity.format.DateSerializer;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import lombok.ToString;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 *
 * @author James
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({Adversary.class, Email.class, Incident.class, Signature.class, Threat.class})
@ToString
public class Group
{

    public static enum Type {
          Adversary
        , Email
        , Incident
        , Signature
        , Threat
    }
    
    @XmlElement(name = "Id", required = true)
    private Integer id;
    @XmlElement(name = "Name", required = true)
    private String name;
    @XmlElement(name = "Type", required = false)
    private String type;
    @XmlElement(name = "Owner", required = false)
    private Owner owner;
    @XmlElement(name = "OwnerName", required = false)
    private String ownerName;
    @JsonSerialize(using = DateSerializer.class)
    @XmlElement(name = "DateAdded", required = false)
    private Date dateAdded;
    @XmlElement(name = "WebLink", required = false)
    private String webLink;
    
    protected Group()
    {
    }
    
    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Owner getOwner()
    {
        return owner;
    }

    public void setOwner(Owner owner)
    {
        this.owner = owner;
    }

    public String getOwnerName()
    {
        return ownerName;
    }

    public void setOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public Date getDateAdded()
    {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded)
    {
        this.dateAdded = dateAdded;
    }

    public String getWebLink()
    {
        return webLink;
    }

    public void setWebLink(String webLink)
    {
        this.webLink = webLink;
    }
    
}
