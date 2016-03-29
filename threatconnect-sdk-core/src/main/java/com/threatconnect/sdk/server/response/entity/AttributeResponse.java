/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity;

import com.threatconnect.sdk.server.entity.Attribute;
import com.threatconnect.sdk.server.response.entity.data.AttributeResponseData;
import com.threatconnect.sdk.server.response.service.ApiServiceResponse;
import com.threatconnect.sdk.server.entity.Attribute;
import com.threatconnect.sdk.server.response.entity.data.AttributeResponseData;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "attributeResponse")
public class AttributeResponse extends ApiEntitySingleResponse<Attribute, AttributeResponseData>
{
    public void setData(AttributeResponseData data) {
        super.setData(data); 
    }
}
