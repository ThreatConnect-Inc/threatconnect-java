package com.threatconnect.sdk.server.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author eric
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlRootElement(name = "File")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({FileOccurrence.class})
public class File extends Indicator
{
    @XmlElement(name = "md5", required = false)
    private String md5;
    @XmlElement(name = "sha1", required = false)
    private String sha1;
    @XmlElement(name = "sha256", required = false)
    private String sha256;
    @XmlElement(name = "size", required = false)
    private Integer size;
    
    public File()
    {
        super();
    }

    public String getMd5()
    {
        return md5;
    }

    public void setMd5(String md5)
    {
        this.md5 = md5;
    }

    public String getSha1()
    {
        return sha1;
    }

    public void setSha1(String sha1)
    {
        this.sha1 = sha1;
    }

    public String getSha256()
    {
        return sha256;
    }

    public void setSha256(String sha256)
    {
        this.sha256 = sha256;
    }

    public Integer getSize()
    {
        return size;
    }

    public void setSize(Integer size)
    {
        this.size = size;
    }
    
}
