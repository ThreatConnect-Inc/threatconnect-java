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
@XmlRootElement(name = "VictimSocialNetwork")
public class VictimSocialNetwork extends VictimAsset
{
    @XmlElement(name = "Account", required = true)
    private String account;
    @XmlElement(name = "Network", required = true)
    private String network;
    
    public VictimSocialNetwork()
    {
    }
    
    public String getAccount()
    {
        return account;
    }

    public void setAccount(String account)
    {
        this.account = account;
    }

    public String getNetwork()
    {
        return network;
    }

    public void setNetwork(String network)
    {
        this.network = network;
    }
    
}
