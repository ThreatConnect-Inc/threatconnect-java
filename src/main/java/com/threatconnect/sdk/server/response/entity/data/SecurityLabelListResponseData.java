/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.threatconnect.sdk.server.entity.SecurityLabel;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author Cole Iliff
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class SecurityLabelListResponseData extends ApiEntityListResponseData<SecurityLabel>
{
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "SecurityLabel", required = false)
    private List<SecurityLabel> securityLabel;
    
    public List<SecurityLabel> getSecurityLabel()
    {
        return securityLabel;
    }

    public void setSecurityLabel(List<SecurityLabel> securityLabel)
    {
        this.securityLabel = securityLabel;
    }

    @Override
    @JsonIgnore
    public List<SecurityLabel> getData()
    {
        return getSecurityLabel();
    }

    @Override
    public void setData(List<SecurityLabel> data)
    {
        setSecurityLabel(data);
    }
}
