/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity;

import com.threatconnect.sdk.server.entity.FileOccurrence;
import com.threatconnect.sdk.server.response.entity.data.FileOccurrenceResponseData;
import com.threatconnect.sdk.server.response.service.ApiServiceResponse;
import com.threatconnect.sdk.server.response.entity.data.FileOccurrenceResponseData;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "fileOccurrenceResponse")
@XmlSeeAlso(FileOccurrence.class)
public class FileOccurrenceResponse extends ApiEntitySingleResponse<FileOccurrence, FileOccurrenceResponseData>
{
    public void setData(FileOccurrenceResponseData data) {
        super.setData(data); 
    }
}
