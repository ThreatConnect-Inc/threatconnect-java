/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.entity;

import com.cyber2.api.lib.server.entity.init.ApiEntityInit;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import org.codehaus.jackson.map.annotate.JsonSerialize;

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
