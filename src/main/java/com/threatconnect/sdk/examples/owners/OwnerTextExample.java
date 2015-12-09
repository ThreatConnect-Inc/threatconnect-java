/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.examples.owners;

import com.threatconnect.sdk.client.reader.OwnerReaderAdapter;
import com.threatconnect.sdk.client.reader.ReaderAdapterFactory;
import com.threatconnect.sdk.util.StringUtil;
import com.threatconnect.sdk.config.Configuration;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.conn.ConnectionUtil;

import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author dtineo
 */
public class OwnerTextExample {

    public static void main(String[] args) {
        
        try {
            Properties props = ConnectionUtil.loadProperties("/config.properties");
            Configuration config = Configuration.build( props );
            Connection conn = new Connection( config );

            OwnerReaderAdapter reader = ReaderAdapterFactory.createOwnerReader(conn);

            String data = reader.getOwnerAsText();
            System.out.println( StringUtil.formatResults(data) );

        } catch (IOException ex) {
            System.err.println("Error: " + ex);
        }
    }
    
}
