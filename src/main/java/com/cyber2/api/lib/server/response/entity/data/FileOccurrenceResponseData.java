/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.response.entity.data;

import com.cyber2.api.lib.server.entity.FileOccurrence;
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
public class FileOccurrenceResponseData extends ApiEntitySingleResponseData<FileOccurrence>
{
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "FileOccurrence", required = false)
    private FileOccurrence fileOccurrence;
    
    public FileOccurrence getFileOccurrence()
    {
        return fileOccurrence;
    }

    public void setFileOccurrence(FileOccurrence fileOccurrence)
    {
        this.fileOccurrence = fileOccurrence;
    }

    @Override
    @JsonIgnore
    public FileOccurrence getData()
    {
        return getFileOccurrence();
    }

    @Override
    public void setData(FileOccurrence data)
    {
        setFileOccurrence(data);
    }
}
