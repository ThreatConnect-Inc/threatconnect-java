/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.response.entity.data;

import com.cyber2.api.lib.server.entity.SecurityLabel;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import org.codehaus.jackson.map.annotate.JsonSerialize;

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
