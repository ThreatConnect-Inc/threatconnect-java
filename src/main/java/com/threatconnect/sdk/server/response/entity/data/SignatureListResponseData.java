/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity.data;

import com.threatconnect.sdk.server.entity.Signature;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class SignatureListResponseData extends ApiEntityListResponseData<Signature>
{
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "Signature", required = false)
    private List<Signature> signature;
    
    public List<Signature> getSignature()
    {
        return signature;
    }

    public void setSignature(List<Signature> signature)
    {
        this.signature = signature;
    }

    @Override
    @JsonIgnore
    public List<Signature> getData()
    {
        return getSignature();
    }

    @Override
    public void setData(List<Signature> data)
    {
        setSignature(data);
    }
}
