/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.threatconnect.sdk.server.entity.Adversary;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class AdversaryResponseData extends ApiEntitySingleResponseData<Adversary>
{
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "Adversary", required = false)
    private Adversary adversary;
    
    public Adversary getAdversary()
    {
        return adversary;
    }

    public void setAdversary(Adversary adversary)
    {
        this.adversary = adversary;
    }

    @Override
    @JsonIgnore
    public Adversary getData()
    {
        return getAdversary();
    }

    @Override
    public void setData(Adversary data)
    {
        setAdversary(data);
    }
}
