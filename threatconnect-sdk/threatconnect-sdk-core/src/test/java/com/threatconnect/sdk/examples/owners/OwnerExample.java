/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.examples.owners;

import com.threatconnect.sdk.app.SdkAppConfig;
import com.threatconnect.sdk.client.response.IterableResponse;
import com.threatconnect.sdk.client.reader.OwnerReaderAdapter;
import com.threatconnect.sdk.client.reader.ReaderAdapterFactory;
import com.threatconnect.sdk.config.Configuration;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.conn.ConnectionUtil;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.entity.Owner;

import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author dtineo
 */
public class OwnerExample {

    public static void main(String[] args) {
        
        try {
            Properties props = ConnectionUtil.loadProperties("/config.properties");
            Configuration config = Configuration.build( props, SdkAppConfig.getInstance());
            Connection conn = new Connection(config);
            OwnerReaderAdapter reader = ReaderAdapterFactory.createOwnerReader(conn);

            IterableResponse<Owner> data = reader.getOwners();
            for(Owner o : data) {
                System.out.println("Owner: " + o.toString() );
            }


        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex);
        }
    }
    
}
