/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.response.entity.data;

import com.cyber2.api.lib.server.entity.VictimNetworkAccount;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class VictimNetworkAccountResponseData extends ApiEntitySingleResponseData<VictimNetworkAccount>
{
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "VictimNetworkAccount", required = false)
    private VictimNetworkAccount victimNetworkAccount;
    
    public VictimNetworkAccount getVictimNetworkAccount()
    {
        return victimNetworkAccount;
    }

    public void setVictimNetworkAccount(VictimNetworkAccount victimNetworkAccount)
    {
        this.victimNetworkAccount = victimNetworkAccount;
    }

    @Override
    public VictimNetworkAccount getData()
    {
        return getVictimNetworkAccount();
    }

    @Override
    public void setData(VictimNetworkAccount data)
    {
        setVictimNetworkAccount(data);
    }
}
