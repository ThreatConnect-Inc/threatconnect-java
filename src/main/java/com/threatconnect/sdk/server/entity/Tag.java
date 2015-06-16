/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

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
@XmlRootElement(name = "Tag")
public class Tag
{
    @XmlElement(name = "Name", required = true)
    private String name;
    @XmlElement(name = "Description", required = false)
    private String description;
    @XmlElement(name = "WebLink", required = false)
    private String webLink;

    public Tag(String name, String description, String webLink)
    {
        this.name = name;
        this.description = description;
        this.webLink = webLink;
    }

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

    @Override
    public String toString()
    {
        return "Tag{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", webLink='" + webLink + '\'' +
                '}';
    }
}
