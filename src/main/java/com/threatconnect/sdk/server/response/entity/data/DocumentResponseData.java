package com.threatconnect.sdk.server.response.entity.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.threatconnect.sdk.server.entity.Document;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by cblades on 4/20/2015.
 */
public class DocumentResponseData extends ApiEntitySingleResponseData<Document>
{

    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "Document", required = false)
    private Document document;

    public Document getDocument()
    {
        return document;
    }

    public void setDocument(Document document)
    {
        this.document = document;
    }

    @Override
    @JsonIgnore
    public Document getData()
    {
        return getDocument();
    }

    @Override
    public void setData(Document data)
    {
        setDocument(data);
    }
}
