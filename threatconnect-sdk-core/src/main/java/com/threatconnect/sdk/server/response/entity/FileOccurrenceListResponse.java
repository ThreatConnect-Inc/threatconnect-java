/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity;

import com.threatconnect.sdk.server.entity.FileOccurrence;
import com.threatconnect.sdk.server.response.entity.data.FileOccurrenceListResponseData;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "fileOccurrencesResponse")
@XmlSeeAlso(FileOccurrence.class)
public class FileOccurrenceListResponse extends ApiEntityListResponse<FileOccurrence, FileOccurrenceListResponseData>
{
    public void setData(FileOccurrenceListResponseData data) {
        super.setData(data); 
    }
}
