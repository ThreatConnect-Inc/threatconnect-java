/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity;

import com.threatconnect.sdk.server.entity.Owner;
import com.threatconnect.sdk.server.response.entity.data.OwnerListResponseData;
import com.threatconnect.sdk.server.response.service.ApiServiceResponse;
import com.threatconnect.sdk.server.entity.Owner;

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
@XmlRootElement(name = "ownersResponse")
@XmlSeeAlso(Owner.class)
public class OwnerListResponse extends ApiEntityListResponse<Owner, OwnerListResponseData>
{
    public void setData(OwnerListResponseData data) {
        super.setData(data);
    }
}
