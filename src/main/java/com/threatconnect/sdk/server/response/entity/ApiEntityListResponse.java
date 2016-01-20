/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.threatconnect.sdk.server.response.entity.data.ApiEntityListResponseData;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author James
 * @param <T>
 * @param <S>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({AddressListResponse.class, AdversaryListResponse.class, AttributeListResponse.class, DnsResolutionListResponse.class, EmailAddressListResponse.class, EmailListResponse.class, FileListResponse.class, 
    HostListResponse.class, GroupListResponse.class, IncidentListResponse.class, IndicatorListResponse.class, OwnerListResponse.class, TagListResponse.class, SecurityLabelListResponse.class, ThreatListResponse.class, 
    UrlListResponse.class, VictimListResponse.class, ObservationListResponse.class, OwnerMetricListResponse.class})
public abstract class ApiEntityListResponse<T, S extends ApiEntityListResponseData<T>>
{
    @XmlElement(name = "Status", required = true)
    private String status;
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "Message", required = false)
    private String message;
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "Data", required = false)
    private ApiEntityListResponseData data;
    
    protected ApiEntityListResponse()
    {
        this.status = "Success";
        this.message = null;
        this.data = null;
    }
    
    public String getStatus()
    {
        return status;
    }

    public String getMessage()
    {
        return message;
    }
    
    public ApiEntityListResponseData getData()
    {
        return data;
    }

    public void setData( ApiEntityListResponseData data)
    {
        this.data = data;
    }
    
    @JsonIgnore
    public boolean isSuccess()
    {
        return ("Success".equals(this.status));
    }
    
    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

}
