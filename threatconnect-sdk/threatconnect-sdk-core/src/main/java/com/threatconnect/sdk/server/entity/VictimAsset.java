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
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author James
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "VictimAsset")
@XmlSeeAlso(
{
    VictimEmailAddress.class, VictimNetworkAccount.class, VictimPhone.class, VictimSocialNetwork.class, VictimWebSite.class
})
public class VictimAsset
{
    @XmlElement(name = "Id", required = true)
    private Integer id;
    @XmlElement(name = "Name", required = false)
    private String name;
    @XmlElement(name = "Type", required = true)
    private String type;
    @XmlElement(name = "WebLink", required = false)
    private String webLink;
    
    public VictimAsset()
    {
    }

    public VictimAsset(Integer id, String name, String type, String webLink)
    {
        this.id = id;
        this.name = name;
        this.type = type;
        this.webLink = webLink;
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

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
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
