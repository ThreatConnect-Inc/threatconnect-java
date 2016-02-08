/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity;

import com.threatconnect.sdk.server.entity.VictimNetworkAccount;
import com.threatconnect.sdk.server.response.entity.data.VictimNetworkAccountListResponseData;
import com.threatconnect.sdk.server.entity.VictimNetworkAccount;
import com.threatconnect.sdk.server.response.entity.data.VictimNetworkAccountListResponseData;

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
