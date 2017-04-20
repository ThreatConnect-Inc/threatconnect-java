/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity;

import com.threatconnect.sdk.server.entity.Signature;
import com.threatconnect.sdk.server.response.entity.data.SignatureResponseData;
import com.threatconnect.sdk.server.response.service.ApiServiceResponse;
import com.threatconnect.sdk.server.entity.Signature;
import com.threatconnect.sdk.server.response.entity.data.SignatureResponseData;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "signatureResponse")
@XmlSeeAlso(Signature.class)
public class SignatureResponse extends ApiEntitySingleResponse<Signature, SignatureResponseData>
{
    public void setData(SignatureResponseData data) {
        super.setData(data); 
    }
}
