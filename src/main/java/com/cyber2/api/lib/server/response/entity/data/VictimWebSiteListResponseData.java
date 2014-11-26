/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.response.entity.data;

import com.cyber2.api.lib.server.entity.VictimWebSite;
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
public class VictimWebSiteListResponseData extends ApiEntityListResponseData<VictimWebSite>
{
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "VictimWebSite", required = false)
    private List<VictimWebSite> victimWebSite;
    
    public List<VictimWebSite> getVictimWebSite()
    {
        return victimWebSite;
    }

    public void setVictimWebSite(List<VictimWebSite> victimWebSite)
    {
        this.victimWebSite = victimWebSite;
    }

    @Override
    @JsonIgnore
    public List<VictimWebSite> getData()
    {
        return getVictimWebSite();
    }

    @Override
    public void setData(List<VictimWebSite> data)
    {
        setVictimWebSite(data);
    }
}
