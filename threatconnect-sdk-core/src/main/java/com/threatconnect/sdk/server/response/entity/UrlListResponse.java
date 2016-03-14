/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity;

import com.threatconnect.sdk.server.entity.Url;
import com.threatconnect.sdk.server.response.entity.data.UrlListResponseData;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "urlsResponse")
@XmlSeeAlso(Url.class)
public class UrlListResponse extends ApiEntityListResponse<Url, UrlListResponseData>
{
    public void setData(UrlListResponseData data) {
        super.setData(data); 
    }
}
