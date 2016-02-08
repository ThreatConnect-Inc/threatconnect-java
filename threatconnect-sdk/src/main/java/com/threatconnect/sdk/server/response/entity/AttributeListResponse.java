/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity;

import com.threatconnect.sdk.server.entity.Attribute;
import com.threatconnect.sdk.server.response.entity.data.AttributeListResponseData;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "attributesResponse")
public class AttributeListResponse extends ApiEntityListResponse<Attribute, AttributeListResponseData>
{
    public void setData(AttributeListResponseData data) {
        super.setData(data); 
    }
}
