package com.cyber2.api.lib.server.response.entity;

import com.cyber2.api.lib.server.entity.Address;
import com.cyber2.api.lib.server.entity.Document;
import com.cyber2.api.lib.server.response.entity.data.AddressResponseData;
import com.cyber2.api.lib.server.response.entity.data.DocumentResponseData;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * Created by cblades on 4/20/2015.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Document")
@XmlSeeAlso(Document.class)
public class DocumentResponse extends ApiEntitySingleResponse<Document, DocumentResponseData> {
    public void setData(DocumentResponseData data) {
        super.setData(data);
    }
}
