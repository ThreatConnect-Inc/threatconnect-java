/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity.data;

import com.threatconnect.sdk.server.entity.Signature;
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
public class SignatureResponseData extends ApiEntitySingleResponseData<Signature>
{
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "Signature", required = false)
    private Signature signature;
    
    public Signature getSignature()
    {
        return signature;
    }

    public void setSignature(Signature signature)
    {
        this.signature = signature;
    }

    @Override
    @JsonIgnore
    public Signature getData()
    {
        return getSignature();
    }

    @Override
    public void setData(Signature data)
    {
        setSignature(data);
    }
}
