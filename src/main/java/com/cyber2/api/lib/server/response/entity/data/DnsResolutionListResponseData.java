/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.response.entity.data;

import com.cyber2.api.lib.server.entity.DnsResolution;
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
public class DnsResolutionListResponseData extends ApiEntityListResponseData<DnsResolution>
{
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "DnsResolution", required = false)
    private List<DnsResolution> dnsResolution;
    
    public List<DnsResolution> getDnsResolution()
    {
        return dnsResolution;
    }

    public void setDnsResolution(List<DnsResolution> dnsResolution)
    {
        this.dnsResolution = dnsResolution;
    }

    @Override
    @JsonIgnore
    public List<DnsResolution> getData()
    {
        return getDnsResolution();
    }

    @Override
    public void setData(List<DnsResolution> data)
    {
        setDnsResolution(data);
    }
}
