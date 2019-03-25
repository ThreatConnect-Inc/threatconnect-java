/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.threatconnect.sdk.server.response.entity.data.ApiEntitySingleResponseData;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author James
 * @param <T> Parameter
 * @param <S> Parameter
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({AddressResponse.class, AdversaryResponse.class, AttributeResponse.class, EmailAddressResponse.class, EmailResponse.class, FileResponse.class, HostResponse.class, IncidentResponse.class, 
    OwnerResponse.class, ThreatResponse.class, UrlResponse.class, VictimResponse.class, BatchResponse.class})
public abstract class ApiEntitySingleResponse<T, S extends ApiEntitySingleResponseData<T>>
{
    @XmlElement(name = "Status", required = true)
    private String status;
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "Message", required = false)
    private String message;
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "Data", required = false)
    private ApiEntitySingleResponseData<T> data;
    
    protected ApiEntitySingleResponse()
    {
        this.status = "Success";
        this.message = null;
        this.data = null;
    }
    
    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public ApiEntitySingleResponseData<T> getData()
    {
        return data;
    }

    public T getItem()
    {
        return data == null ? null : data.getData();
    }

    public void setData(ApiEntitySingleResponseData<T> data) {
        this.data = data;
    }
    
    @JsonIgnore
    public boolean isSuccess()
    {
        return ("Success".equals(this.status));
    }
    
}
