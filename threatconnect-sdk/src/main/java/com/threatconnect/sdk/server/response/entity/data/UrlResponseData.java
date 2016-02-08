/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.threatconnect.sdk.server.entity.Url;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class UrlResponseData extends ApiEntitySingleResponseData<Url>
{
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "Url", required = false)
    private Url url;
    
    public Url getUrl()
    {
        return url;
    }

    public void setUrl(Url url)
    {
        this.url = url;
    }

    @Override
    @JsonIgnore
    public Url getData()
    {
        return getUrl();
    }

    @Override
    public void setData(Url data)
    {
        setUrl(data);
    }
}
