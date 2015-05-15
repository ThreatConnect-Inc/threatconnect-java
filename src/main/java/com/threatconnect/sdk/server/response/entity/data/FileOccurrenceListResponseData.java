/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity.data;

import com.threatconnect.sdk.server.entity.FileOccurrence;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class FileOccurrenceListResponseData extends ApiEntityListResponseData<FileOccurrence>
{
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "FileOccurrence", required = false)
    private List<FileOccurrence> fileOccurrence;
    
    public List<FileOccurrence> getFileOccurrence()
    {
        return fileOccurrence;
    }

    public void setFileOccurrence(List<FileOccurrence> fileOccurrence)
    {
        this.fileOccurrence = fileOccurrence;
    }

    @Override
    @JsonIgnore
    public List<FileOccurrence> getData()
    {
        return getFileOccurrence();
    }

    @Override
    public void setData(List<FileOccurrence> data)
    {
        setFileOccurrence(data);
    }
}
