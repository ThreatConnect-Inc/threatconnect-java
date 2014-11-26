/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.response.entity;

import com.cyber2.api.lib.server.entity.Adversary;
import com.cyber2.api.lib.server.response.entity.data.AdversaryListResponseData;
import com.cyber2.api.lib.server.response.service.ApiServiceResponse;
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
@XmlRootElement(name = "adversariesResponse")
@XmlSeeAlso(Adversary.class)
public class AdversaryListResponse extends ApiEntityListResponse<Adversary, AdversaryListResponseData>
{
    public void setData(AdversaryListResponseData data) {
        super.setData(data); 
    }
}
