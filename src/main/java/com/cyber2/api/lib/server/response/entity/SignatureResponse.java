/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.response.entity;

import com.cyber2.api.lib.server.entity.Signature;
import com.cyber2.api.lib.server.response.entity.data.SignatureResponseData;
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
@XmlRootElement(name = "signatureResponse")
@XmlSeeAlso(Signature.class)
public class SignatureResponse extends ApiEntitySingleResponse<Signature, SignatureResponseData>
{
    public void setData(SignatureResponseData data) {
        super.setData(data); 
    }
}
