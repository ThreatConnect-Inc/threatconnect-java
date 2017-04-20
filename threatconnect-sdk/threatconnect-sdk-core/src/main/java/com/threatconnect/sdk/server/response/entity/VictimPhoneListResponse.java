/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity;

import com.threatconnect.sdk.server.entity.VictimPhone;
import com.threatconnect.sdk.server.response.entity.data.VictimPhoneListResponseData;
import com.threatconnect.sdk.server.response.service.ApiServiceResponse;
import com.threatconnect.sdk.server.entity.VictimPhone;
import com.threatconnect.sdk.server.response.entity.data.VictimPhoneListResponseData;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "victimPhonesResponse")
@XmlSeeAlso(VictimPhone.class)
public class VictimPhoneListResponse extends ApiEntityListResponse<VictimPhone, VictimPhoneListResponseData>
{
    public void setData(VictimPhoneListResponseData data) {
        super.setData(data); 
    }
}
