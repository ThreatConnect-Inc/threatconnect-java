/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.examples.owners;

import com.cyber2.api.lib.client.reader.OwnerReaderAdapter;
import com.cyber2.api.lib.client.reader.ReaderAdapterFactory;
import com.cyber2.api.lib.util.StringUtil;
import com.cyber2.api.lib.config.Configuration;
import com.cyber2.api.lib.conn.Connection;
import com.cyber2.api.lib.conn.ConnectionUtil;
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
