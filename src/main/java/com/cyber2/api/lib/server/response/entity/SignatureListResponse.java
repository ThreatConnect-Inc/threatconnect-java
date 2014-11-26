/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.response.entity;

import com.cyber2.api.lib.server.entity.Signature;
import com.cyber2.api.lib.server.response.entity.data.SignatureListResponseData;
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
@XmlRootElement(name = "signaturesResponse")
@XmlSeeAlso(Signature.class)
public class SignatureListResponse extends ApiEntityListResponse<Signature, SignatureListResponseData>
{
    public void setData(SignatureListResponseData data) {
        super.setData(data); 
    }
}
