/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 *
 * @author James
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlRootElement(name = "Threat")
@XmlAccessorType(XmlAccessType.FIELD)
public class Threat extends Group
{
    public Threat()
    {
        super();
    }

    public Threat(Integer id, String name, String type, Owner owner, String ownerName, Date dateAdded, String webLink)
    {
        super(id, name, type, owner, ownerName, dateAdded, webLink);
    }
}
    
