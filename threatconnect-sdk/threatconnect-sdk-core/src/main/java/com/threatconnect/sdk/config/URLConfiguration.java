/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.config;

import com.threatconnect.sdk.conn.ConnectionUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author dtineo
 */
public class URLConfiguration {

    private final HashMap<String, String> urlMap = new HashMap<>();

    private URLConfiguration() { }

    public static URLConfiguration build() throws IOException {
        Properties props = ConnectionUtil.loadProperties("/urls.properties");
        URLConfiguration cc = new URLConfiguration();
        for (Map.Entry<Object, Object> entry : props.entrySet()) {
            cc.urlMap.put(entry.getKey().toString(), entry.getValue().toString());
        }

        return cc;
    }

    public String getUrl(String label) {
        return urlMap.get(label);
    }
}
