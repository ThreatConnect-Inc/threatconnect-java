/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.examples.groups;

import com.cyber2.api.lib.client.reader.AbstractGroupReaderAdapter;
import com.cyber2.api.lib.client.reader.ReaderAdapterFactory;
import com.cyber2.api.lib.util.StringUtil;
import com.cyber2.api.lib.config.Configuration;
import com.cyber2.api.lib.conn.Connection;
import com.cyber2.api.lib.conn.ConnectionUtil;
import com.cyber2.api.lib.server.entity.Adversary;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author dtineo
 */
public class GroupTextExample {

    public static void main(String[] args) {
        
        try {
            Properties props = ConnectionUtil.loadProperties("/config.properties");
            Configuration config = Configuration.build( props );
            Connection conn = new Connection( config );
            AbstractGroupReaderAdapter<Adversary> reader = ReaderAdapterFactory.createAdversaryGroupReader(conn);

            String data = reader.getGroupsAsText();
            System.out.println( StringUtil.formatResults(data) );

        } catch (IOException ex) {
            System.err.println("Error: " + ex);
        }
    }
    
}
