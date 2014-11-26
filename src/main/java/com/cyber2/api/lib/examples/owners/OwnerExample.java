/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.examples.owners;

import com.cyber2.api.lib.client.reader.OwnerReaderAdapter;
import com.cyber2.api.lib.client.reader.ReaderAdapterFactory;
import com.cyber2.api.lib.config.Configuration;
import com.cyber2.api.lib.conn.Connection;
import com.cyber2.api.lib.conn.ConnectionUtil;
import com.cyber2.api.lib.exception.FailedResponseException;
import com.cyber2.api.lib.server.entity.Owner;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author dtineo
 */
public class OwnerExample {

    public static void main(String[] args) {
        
        try {
            Properties props = ConnectionUtil.loadProperties("/config.properties");
            Configuration config = Configuration.build( props );
            Connection conn = new Connection(config);
            OwnerReaderAdapter reader = ReaderAdapterFactory.createOwnerReader(conn);

            List<Owner> data = reader.getOwners();
            for(Owner o : data) {
                System.out.println("Owner: " + o.toString() );
            }


        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex);
        }
    }
    
}
