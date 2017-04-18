/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity.data;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author James
 * @param <T> Parameter
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({AddressResponseData.class, AdversaryResponseData.class, AttributeResponseData.class, EmailAddressResponseData.class, EmailResponseData.class, FileResponseData.class, HostResponseData.class, 
    IncidentResponseData.class, OwnerResponseData.class, SignatureResponseData.class, TagResponseData.class, SecurityLabelResponseData.class, ThreatResponseData.class, UrlResponseData.class, VictimAssetResponseData.class,
    VictimEmailAddressResponseData.class, VictimNetworkAccountResponseData.class, VictimPhoneResponseData.class, VictimSocialNetworkResponseData.class, VictimWebSiteResponseData.class, VictimResponseData.class})
public abstract class ApiEntitySingleResponseData<T>
{
    @JsonIgnore
    public abstract T getData();
    public abstract void setData(T data);
    
    public ApiEntitySingleResponseData()
    {
        setData(null);
    }
    
    public ApiEntitySingleResponseData(T data)
    {
        setData(data);
    }
}
