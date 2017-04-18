/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity.data;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.threatconnect.sdk.server.entity.SecurityLabel;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author Cole Iliff
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class SecurityLabelResponseData extends ApiEntitySingleResponseData<SecurityLabel>
{
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "SecurityLabel", required = false)
    private SecurityLabel securityLabel;
    
    public SecurityLabel getSecurityLabel()
    {
        return securityLabel;
    }

    public void setSecurityLabel(SecurityLabel securityLabel)
    {
        this.securityLabel = securityLabel;
    }

    @Override
    public SecurityLabel getData()
    {
        return getSecurityLabel();
    }

    @Override
    public void setData(SecurityLabel data)
    {
        setSecurityLabel(data);
    }
}
