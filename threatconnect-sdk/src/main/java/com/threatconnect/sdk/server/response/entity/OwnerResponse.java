/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity;

import com.threatconnect.sdk.server.entity.Owner;
import com.threatconnect.sdk.server.response.entity.data.OwnerResponseData;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ownerResponse")
@XmlSeeAlso(Owner.class)
public class OwnerResponse extends ApiEntitySingleResponse<Owner, OwnerResponseData>
{
    public void setData(OwnerResponseData data) {
        super.setData(data); 
    }
}
