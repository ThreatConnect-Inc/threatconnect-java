/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.response.entity;

import com.cyber2.api.lib.server.entity.FileOccurrence;
import com.cyber2.api.lib.server.response.entity.data.FileOccurrenceResponseData;
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
@XmlRootElement(name = "fileOccurrenceResponse")
@XmlSeeAlso(FileOccurrence.class)
public class FileOccurrenceResponse extends ApiEntitySingleResponse<FileOccurrence, FileOccurrenceResponseData>
{
    public void setData(FileOccurrenceResponseData data) {
        super.setData(data); 
    }
}
