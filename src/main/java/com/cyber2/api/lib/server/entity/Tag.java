/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.ToString;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 *
 * @author eric
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Tag")
@ToString
public class Tag
{
    @XmlElement(name = "Name", required = true)
    private String name;
    @XmlElement(name = "Description", required = false)
    private String description;
    @XmlElement(name = "WebLink", required = false)
    private String webLink;
    
    public Tag()
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

    public String getWebLink()
    {
        return webLink;
    }

    public void setWebLink(String webLink)
    {
        this.webLink = webLink;
    }
    
}
