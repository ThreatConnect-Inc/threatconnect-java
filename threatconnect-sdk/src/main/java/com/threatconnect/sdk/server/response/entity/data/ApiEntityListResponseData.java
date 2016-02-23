/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author James
 * @param <T> Parameter
 */
@XmlAccessorType(XmlAccessType.FIELD)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlSeeAlso({AddressListResponseData.class, AdversaryListResponseData.class, AttributeListResponseData.class, DnsResolutionListResponseData.class, EmailAddressListResponseData.class, EmailListResponseData.class, 
    FileListResponseData.class, GroupListResponseData.class, HostListResponseData.class, IncidentListResponseData.class, IndicatorListResponseData.class, OwnerListResponseData.class, SignatureListResponseData.class,
    TagListResponseData.class, SecurityLabelListResponseData.class, ThreatListResponseData.class, UrlListResponseData.class, VictimAssetListResponseData.class, VictimEmailAddressListResponseData.class, 
    VictimNetworkAccountListResponseData.class, VictimPhoneListResponseData.class, VictimSocialNetworkListResponseData.class, VictimWebSiteListResponseData.class, VictimListResponseData.class})
public abstract class ApiEntityListResponseData<T>
{
    @XmlElement(name = "ResultCount", required = false)
    private Integer resultCount = null;
    @JsonIgnore
    public abstract List<T> getData();

    public abstract void setData(List<T> data);
    
    public ApiEntityListResponseData()
    {
        setData(null);
    }
    
    public ApiEntityListResponseData(List<T> data)
    {
        setData(data);
    }

    public Integer getResultCount()
    {
        return resultCount;
    }

    public void setResultCount(Integer resultCount)
    {
        this.resultCount = resultCount;
    }
}
