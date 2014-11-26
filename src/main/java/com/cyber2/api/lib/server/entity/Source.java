/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author James
 */
@XmlRootElement(name = "Owner")
@XmlAccessorType(XmlAccessType.FIELD)
public class Source extends Owner
{
    public Source()
    {
        super();
    }
    
    @Override
    public String getType()
    {
        return "Source";
    }
    
}
