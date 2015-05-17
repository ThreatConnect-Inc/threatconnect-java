/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author James
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Email")
public class Email extends Group
{
    @XmlElement(name = "To", required = true)
    private String to;
    @XmlElement(name = "From", required = true)
    private String from;
    @XmlElement(name = "Subject", required = true)
    private String subject;
    @XmlElement(name = "Score", required = true)
    private Integer score;
    @XmlElement(name = "Header", required = true)
    private String header;
    @XmlElement(name = "Body", required = true)
    private String body;
    
    public Email()
    {
        super();
    }
    
    /**
     * @return the to
     */
    public String getTo()
    {
        return to;
    }

    /**
     * @param to the to to set
     */
    public void setTo(String to)
    {
        this.to = to;
    }

    /**
     * @return the from
     */
    public String getFrom()
    {
        return from;
    }

    /**
     * @param from the from to set
     */
    public void setFrom(String from)
    {
        this.from = from;
    }

    /**
     * @return the subject
     */
    public String getSubject()
    {
        return subject;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    /**
     * @return the score
     */
    public Integer getScore()
    {
        return score;
    }

    /**
     * @param score the score to set
     */
    public void setScore(Integer score)
    {
        this.score = score;
    }

    /**
     * @return the header
     */
    public String getHeader()
    {
        return header;
    }

    /**
     * @param header the header to set
     */
    public void setHeader(String header)
    {
        this.header = header;
    }

    /**
     * @return the body
     */
    public String getBody()
    {
        return body;
    }

    /**
     * @param body the body to set
     */
    public void setBody(String body)
    {
        this.body = body;
    }
    
}
