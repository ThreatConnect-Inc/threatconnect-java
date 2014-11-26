/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.response.entity;

import com.cyber2.api.lib.server.entity.VictimNetworkAccount;
import com.cyber2.api.lib.server.response.entity.data.VictimNetworkAccountListResponseData;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "victimNetworkAccounts")
@XmlSeeAlso(VictimNetworkAccount.class)
public class VictimNetworkAccountListResponse extends ApiEntityListResponse<VictimNetworkAccount, VictimNetworkAccountListResponseData>
{
    public void setData(VictimNetworkAccountListResponseData data) {
        super.setData(data); 
    }
}
