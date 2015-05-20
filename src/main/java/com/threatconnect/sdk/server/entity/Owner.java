/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author eric
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Owner")
@XmlSeeAlso({Community.class, Individual.class, Organization.class})
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include= JsonTypeInfo.As.PROPERTY, property="type")
  @JsonSubTypes({
        @JsonSubTypes.Type(value=Community.class, name="Community"),
        @JsonSubTypes.Type(value=Individual.class, name="Individual"),
        @JsonSubTypes.Type(value=Organization.class, name="Organization"),
        @JsonSubTypes.Type(value=Source.class, name="Source")

    }) 
public abstract class Owner
{
    @XmlElement(name = "Id", required = true)
    private Integer id;
    @XmlElement(name = "Name", required = true)
    private String name;
    
    @XmlElement(name = "Type")
    private String type;
    
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

    public void setType(String type)
    {
        this.type = type;
    }
    
    public String getType()
    {
        return this.type;
    }

    @Override
    public String toString()
    {
        return "Owner{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
