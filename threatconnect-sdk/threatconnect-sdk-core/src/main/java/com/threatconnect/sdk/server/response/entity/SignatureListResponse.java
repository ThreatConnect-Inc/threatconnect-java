/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity;

import com.threatconnect.sdk.server.entity.Signature;
import com.threatconnect.sdk.server.response.entity.data.SignatureListResponseData;
import com.threatconnect.sdk.server.response.service.ApiServiceResponse;
import com.threatconnect.sdk.server.response.entity.data.SignatureListResponseData;

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
@XmlRootElement(name = "signaturesResponse")
@XmlSeeAlso(Signature.class)
public class SignatureListResponse extends ApiEntityListResponse<Signature, SignatureListResponseData>
{
    public void setData(SignatureListResponseData data) {
        super.setData(data); 
    }
}
