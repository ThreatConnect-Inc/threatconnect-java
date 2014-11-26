/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.response.entity.data;

import com.cyber2.api.lib.server.entity.Host;
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
public class HostListResponseData extends ApiEntityListResponseData<Host>
{
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "Host", required = false)
    private List<Host> host;
    
    public List<Host> getHost()
    {
        return host;
    }

    public void setHost(List<Host> host)
    {
        this.host = host;
    }

    @Override
    @JsonIgnore
    public List<Host> getData()
    {
        return getHost();
    }

    @Override
    public void setData(List<Host> data)
    {
        setHost(data);
    }
}
