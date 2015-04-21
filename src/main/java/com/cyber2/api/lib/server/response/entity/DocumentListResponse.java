package com.cyber2.api.lib.server.response.entity;

import com.cyber2.api.lib.server.entity.Address;
import com.cyber2.api.lib.server.entity.Document;
import com.cyber2.api.lib.server.response.entity.data.DocumentListResponseData;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.List;

/**
 * Created by cblades on 4/20/2015.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "documentResponse")
@XmlSeeAlso(Document.class)
public class DocumentListResponse extends ApiEntityListResponse<Document, DocumentListResponseData>
{
    public void setData(DocumentListResponseData data)
    {
        super.setData(data);
    }
}
