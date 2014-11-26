/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.response.entity.data;

import com.cyber2.api.lib.server.entity.File;
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
