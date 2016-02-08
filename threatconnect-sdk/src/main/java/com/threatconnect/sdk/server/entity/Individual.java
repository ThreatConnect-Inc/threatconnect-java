/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author James
 */
@XmlRootElement(name = "Owner")
@XmlAccessorType(XmlAccessType.FIELD)
public class Individual extends Owner
{
    public Individual(Integer id, String name, String type)
    {
        super(id, name, type);
    }

    public Individual()
    {
        super();
        super.setType("Individual");
    }
    
}
