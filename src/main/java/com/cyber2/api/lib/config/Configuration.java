/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.config;

import java.util.Properties;
import org.apache.http.entity.ContentType;

/**
 *
 * @author dtineo
 */
public class Configuration {

    private String tcApiUrl;
    private String tcApiAccessID;
    private String tcApiUserSecretKey;

    private String contentType = ContentType.APPLICATION_JSON.getMimeType();

    public static Configuration build(Properties props) {

        Configuration cc = new Configuration();
        
        cc.tcApiUrl = props.getProperty("connection.tcApiUrl");
        cc.tcApiAccessID = props.getProperty("connection.tcApiAccessID");
        cc.tcApiUserSecretKey = props.getProperty("connection.tcApiUserSecretKey");
        cc.contentType = props.getProperty("connection.contentType");

        return cc;
    }


    /**
     * @return the tcApiUrl
     */
    public String getTcApiUrl() {
        return tcApiUrl;
    }

    /**
     * @param tcApiUrl the tcApiUrl to set
     */
    public void setTcApiUrl(String tcApiUrl) {
        this.tcApiUrl = tcApiUrl;
    }

    /**
     * @return the tcApiAccessID
     */
    public String getTcApiAccessID() {
        return tcApiAccessID;
    }

    /**
     * @param tcAccessID the tcApiAccessID to set
     */
    public void setTcApiAccessID(String tcAccessID) {
        this.tcApiAccessID = tcAccessID;
    }

    /**
     * @return the tcApiUserSecretKey
     */
    public String getTcApiUserSecretKey() {
        return tcApiUserSecretKey;
    }

    /**
     * @param tcApiUserSecretKey the tcApiUserSecretKey to set
     */
    public void setTcApiUserSecretKey(String tcApiUserSecretKey) {
        this.tcApiUserSecretKey = tcApiUserSecretKey;
    }

    /**
     * @return the contentType
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * @param contentType the contentType to set
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    
}
