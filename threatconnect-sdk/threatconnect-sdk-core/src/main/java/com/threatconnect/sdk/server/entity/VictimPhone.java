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
 * @author James
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "VictimPhone")
public class VictimPhone extends VictimAsset
{
    @XmlElement(name = "PhoneType", required = true)
    private String phoneType;
    
    public VictimPhone()
    {
    }

    public VictimPhone(Long id, String name, String type, String webLink, String phoneType)
    {
        super(id, name, type, webLink);
        this.phoneType = phoneType;
    }

    public String getPhoneType()
    {
        return phoneType;
    }

    public void setPhoneType(String phoneType)
    {
        this.phoneType = phoneType;
    }
    
}
