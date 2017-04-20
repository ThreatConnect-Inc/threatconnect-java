/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity;

import com.threatconnect.sdk.server.entity.File;
import com.threatconnect.sdk.server.response.entity.data.FileListResponseData;
import com.threatconnect.sdk.server.response.service.ApiServiceResponse;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "filesResponse")
@XmlSeeAlso(File.class)
public class FileListResponse extends ApiEntityListResponse<File, FileListResponseData>
{
    public void setData(FileListResponseData data) {
        super.setData(data); 
    }
}
