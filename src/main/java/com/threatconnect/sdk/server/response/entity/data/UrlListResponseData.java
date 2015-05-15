/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity.data;

import com.threatconnect.sdk.server.entity.Url;
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
public class UrlListResponseData extends ApiEntityListResponseData<Url>
{
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "Url", required = false)
    private List<Url> url;
    
    public List<Url> getUrl()
    {
        return url;
    }

    public void setUrl(List<Url> url)
    {
        this.url = url;
    }

    @Override
    @JsonIgnore
    public List<Url> getData()
    {
        return getUrl();
    }

    @Override
    public void setData(List<Url> data)
    {
        setUrl(data);
    }
}
