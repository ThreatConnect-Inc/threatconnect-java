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
 * @author eric
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Adversary")
public class Adversary extends Group
{
    public Adversary()
    {
        super();
    }

    public Adversary(Long id, String name, String type, Owner owner, String ownerName, Date dateAdded, String webLink)
    {
        super(id, name, type, owner, ownerName, dateAdded, webLink);
    }

    @Override
    public String toString()
    {
        return "Adversary{}" + super.toString();
    }
}
