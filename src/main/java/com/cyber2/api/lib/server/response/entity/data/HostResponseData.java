/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.response.entity.data;

import com.cyber2.api.lib.server.entity.Host;
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
public class HostResponseData extends ApiEntitySingleResponseData<Host>
{
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "Host", required = false)
    private Host host;
    
    public Host getHost()
    {
        return host;
    }

    public void setHost(Host host)
    {
        this.host = host;
    }

    @Override
    @JsonIgnore
    public Host getData()
    {
        return getHost();
    }

    @Override
    public void setData(Host data)
    {
        setHost(data);
    }
}
