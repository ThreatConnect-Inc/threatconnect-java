/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity;

import com.threatconnect.sdk.server.entity.Tag;
import com.threatconnect.sdk.server.response.entity.data.TagListResponseData;
import com.threatconnect.sdk.server.response.service.ApiServiceResponse;
import com.threatconnect.sdk.server.entity.Tag;
import com.threatconnect.sdk.server.response.entity.data.TagListResponseData;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "tagsResponse")
public class TagListResponse extends ApiEntityListResponse<Tag, TagListResponseData>
{
    public void setData(TagListResponseData data) {
        super.setData(data); 
    }
}
