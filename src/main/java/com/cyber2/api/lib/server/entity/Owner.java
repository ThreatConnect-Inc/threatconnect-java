/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import lombok.ToString;
import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeInfo.As;

/**
 *
 * @author eric
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Owner")
@XmlSeeAlso({Community.class, Individual.class, Organization.class})
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=As.PROPERTY, property="type")
  @JsonSubTypes({
        @JsonSubTypes.Type(value=Community.class, name="Community"),
        @JsonSubTypes.Type(value=Individual.class, name="Individual"),
        @JsonSubTypes.Type(value=Organization.class, name="Organization")

    }) 
@ToString
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
}
