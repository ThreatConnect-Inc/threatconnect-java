/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.threatconnect.sdk.server.entity.File;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class FileListResponseData extends ApiEntityListResponseData<File>
{
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "File", required = false)
    private List<File> file;
    
    public List<File> getFile()
    {
        return file;
    }

    public void setFile(List<File> file)
    {
        this.file = file;
    }

    @Override
    @JsonIgnore
    public List<File> getData()
    {
        return getFile();
    }

    @Override
    public void setData(List<File> data)
    {
        setFile(data);
    }
}
