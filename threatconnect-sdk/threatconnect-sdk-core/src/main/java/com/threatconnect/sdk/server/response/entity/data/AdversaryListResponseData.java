/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.threatconnect.sdk.server.entity.Adversary;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class AdversaryListResponseData extends ApiEntityListResponseData<Adversary>
{
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "Adversary", required = false)
    private List<Adversary> adversary;
    
    public List<Adversary> getAdversary()
    {
        return adversary;
    }

    public void setAdversary(List<Adversary> adversary)
    {
        this.adversary = adversary;
    }

    @Override
    @JsonIgnore
    public List<Adversary> getData()
    {
        return getAdversary();
    }

    @Override
    public void setData(List<Adversary> data)
    {
        setAdversary(data);
    }
}
