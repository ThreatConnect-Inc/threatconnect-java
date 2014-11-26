/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.response.entity;

import com.cyber2.api.lib.server.entity.Url;
import com.cyber2.api.lib.server.response.entity.data.UrlResponseData;
import com.cyber2.api.lib.server.response.service.ApiServiceResponse;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "urlResponse")
@XmlSeeAlso(Url.class)
public class UrlResponse extends ApiEntitySingleResponse<Url, UrlResponseData>
{
    public void setData(UrlResponseData data) {
        super.setData(data); 
    }
}
