/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.response.entity;

import com.cyber2.api.lib.server.entity.Attribute;
import com.cyber2.api.lib.server.response.entity.data.AttributeResponseData;
import com.cyber2.api.lib.server.response.service.ApiServiceResponse;
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
