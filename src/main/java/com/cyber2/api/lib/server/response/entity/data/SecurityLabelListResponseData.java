/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.response.entity.data;

import com.cyber2.api.lib.server.entity.SecurityLabel;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

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
