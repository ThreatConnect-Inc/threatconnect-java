/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.response.entity;

import com.cyber2.api.lib.server.entity.File;
import com.cyber2.api.lib.server.response.entity.data.FileResponseData;
import com.cyber2.api.lib.server.response.service.ApiServiceResponse;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "fileResponse")
@XmlSeeAlso(File.class)
public class FileResponse extends ApiEntitySingleResponse<File, FileResponseData>
{
    public void setData(FileResponseData data) {
        super.setData(data); 
    }
}
