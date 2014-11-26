/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.response.entity;

import com.cyber2.api.lib.server.entity.SecurityLabel;
import com.cyber2.api.lib.server.response.entity.data.SecurityLabelResponseData;
import com.cyber2.api.lib.server.response.service.ApiServiceResponse;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author Cole
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "securityLabelResponse")
@XmlSeeAlso(SecurityLabel.class)
public class SecurityLabelResponse extends ApiEntitySingleResponse<SecurityLabel, SecurityLabelResponseData>
{
    public void setData(SecurityLabelResponseData data) {
        super.setData(data); 
    }
}
