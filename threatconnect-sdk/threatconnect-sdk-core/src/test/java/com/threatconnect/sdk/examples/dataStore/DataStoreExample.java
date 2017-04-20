package com.threatconnect.sdk.examples.dataStore;

import com.threatconnect.sdk.client.reader.DataStoreReaderAdapter;
import com.threatconnect.sdk.client.reader.ReaderAdapterFactory;
import com.threatconnect.sdk.client.writer.DataStoreWriterAdapter;
import com.threatconnect.sdk.client.writer.WriterAdapterFactory;
import com.threatconnect.sdk.conn.Connection;

import java.io.IOException;



/**
 * Created by cblades on 7/14/2016.
 */
public class DataStoreExample
{
    public static void main(String[] args) throws InterruptedException
    {
        System.setProperty("org.apache.commons.logging.Log","org.apache.commons.logging.impl.SimpleLog");
        System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
        System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http.wire", "DEBUG");
        System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http.impl.conn", "DEBUG");
        System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http.impl.client", "DEBUG");
        System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http.client", "DEBUG");
        System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http", "DEBUG");


        Connection conn = null;

        try {

            System.getProperties().setProperty("threatconnect.api.config", "/config.properties");
            conn = new Connection();

            create(conn);
            update(conn);
            Thread.sleep(1000);
            get(conn);
            delete(conn);
            get(conn);

        } catch (IOException ex ) {
            System.err.println("Error: " + ex);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    public static void create(Connection conn)
    {
        DataStoreWriterAdapter writerAdapater = WriterAdapterFactory.createDataStoreWriterAdapter(conn);

        System.out.println(writerAdapater.createOrganization("example", "1", "System", "{\"name\": \"Fred\"}"));
        System.out.println(writerAdapater.createOrganization("example", "2", "System", "{\"name\": \"Jack\"}"));
        System.out.println(writerAdapater.createOrganization("example", "3", "System", "{\"name\": \"John\"}"));

    }

    public static void update(Connection conn)
    {
        DataStoreWriterAdapter writerAdapater = WriterAdapterFactory.createDataStoreWriterAdapter(conn);

        System.out.println(writerAdapater.updateOrganization("example", "1", "System", "{\"name\": \"Bob\"}"));
    }


    public static void get(Connection conn)
    {
        DataStoreReaderAdapter readerAdapter = ReaderAdapterFactory.createDataStoreReaderAdapter(conn);

        System.out.println(
                readerAdapter.getOrganization("example", "_search", "System"));
    }

    public static void delete(Connection conn)
    {
        DataStoreWriterAdapter writerAdapater = WriterAdapterFactory.createDataStoreWriterAdapter(conn);

        System.out.println(writerAdapater.deleteOrganization("example", "1", "System"));
    }
}
