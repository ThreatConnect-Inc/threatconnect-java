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
@XmlRootElement(name = "VictimEmailAddress")
public class VictimEmailAddress extends VictimAsset
{
    @XmlElement(name = "Address", required = true)
    private String address;
    @XmlElement(name = "AddressType", required = true)
    private String addressType;
    
    public VictimEmailAddress()
    {
    }

    public VictimEmailAddress(Long id, String name, String type, String webLink, String address, String addressType)
    {
        super(id, name, type, webLink);
        this.address = address;
        this.addressType = addressType;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getAddressType()
    {
        return addressType;
    }

    public void setAddressType(String addressType)
    {
        this.addressType = addressType;
    }
    
}
