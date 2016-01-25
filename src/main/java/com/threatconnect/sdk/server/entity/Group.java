/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.threatconnect.sdk.server.entity.format.DateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author James
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlSeeAlso({Adversary.class, Email.class, Incident.class, Signature.class, Threat.class})
public class Group implements AttributeHolder
{

    public Group(Integer id, String name, String type, Owner owner, String ownerName, Date dateAdded, String webLink)
    {
        this.id = id;
        this.name = name;
        this.type = type;
        this.owner = owner;
        this.ownerName = ownerName;
        this.dateAdded = dateAdded;
        this.webLink = webLink;
    }


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

    private Map<String,String> attributes = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

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

    @JsonIgnore
    public Owner getOwner()
    {
        return owner;
    }

    @JsonProperty("owner")
    public void setOwner(Owner owner)
    {
        this.owner = owner;
    }

    public String getOwnerName()
    {
        return (ownerName == null && this.owner != null) ? owner.getName() : ownerName;
    }

    public void setOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
    }

    public String getType()
    {
        return type == null ? getClass().getSimpleName().toLowerCase() : type;
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

    @Override
    @JsonIgnore
    public Map<String, String> getAttributes()
    {
        return attributes;
    }

    @Override
    public String toString()
    {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", owner=" + owner +
                ", ownerName='" + ownerName + '\'' +
                ", dateAdded=" + dateAdded +
                ", webLink='" + webLink + '\'' +
                '}';
    }
}
