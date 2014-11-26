/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.response.entity.data;

import com.cyber2.api.lib.server.entity.VictimNetworkAccount;
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
public class VictimNetworkAccountListResponseData extends ApiEntityListResponseData<VictimNetworkAccount>
{
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "VictimNetworkAccount", required = false)
    private List<VictimNetworkAccount> victimNetworkAccount;
    
    public List<VictimNetworkAccount> getVictimNetworkAccount()
    {
        return victimNetworkAccount;
    }

    public void setVictimNetworkAccount(List<VictimNetworkAccount> victimNetworkAccount)
    {
        this.victimNetworkAccount = victimNetworkAccount;
    }

    @Override
    @JsonIgnore
    public List<VictimNetworkAccount> getData()
    {
        return getVictimNetworkAccount();
    }

    @Override
    public void setData(List<VictimNetworkAccount> data)
    {
        setVictimNetworkAccount(data);
    }
}
