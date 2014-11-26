/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.response.entity;

import com.cyber2.api.lib.server.entity.SecurityLabel;
import com.cyber2.api.lib.server.response.entity.data.SecurityLabelListResponseData;
import com.cyber2.api.lib.server.response.service.ApiServiceResponse;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Cole Iliff
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "securityLabelsResponse")
public class SecurityLabelListResponse extends ApiEntityListResponse<SecurityLabel, SecurityLabelListResponseData>
{
    public void setData(SecurityLabelListResponseData data) {
        super.setData(data); 
    }
}
