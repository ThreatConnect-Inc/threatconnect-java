/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.threatconnect.sdk.server.entity.DnsResolution;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

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
