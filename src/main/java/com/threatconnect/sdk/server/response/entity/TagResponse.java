/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity;

import com.threatconnect.sdk.server.entity.Tag;
import com.threatconnect.sdk.server.response.entity.data.TagResponseData;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "tagResponse")
@XmlSeeAlso(Tag.class)
public class TagResponse extends ApiEntitySingleResponse<Tag, TagResponseData>
{
    public void setData(TagResponseData data) {
        super.setData(data); 
    }
}
