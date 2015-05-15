/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 *
 * @author James
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Signature")
public class Signature extends Group
{
    @XmlElement(name = "fileType", required = false)
    private String fileType;
    @XmlElement(name = "fileName", required = false)
    private String fileName;
    @XmlElement(name = "fileText", required = false)
    private String fileText;
    
    public Signature()
    {
        super();
    }
    
    public String getFileType()
    {
        return fileType;
    }

    public void setFileType(String fileType)
    {
        this.fileType = fileType;
    }

    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public String getFileText()
    {
        return fileText;
    }

    public void setFileText(String fileText)
    {
        this.fileText = fileText;
    }
    
}
