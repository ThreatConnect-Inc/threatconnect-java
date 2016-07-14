package com.threatconnect.sdk.examples.dataStore;

import com.threatconnect.sdk.client.reader.DataStoreReaderAdapter;
import com.threatconnect.sdk.client.reader.ReaderAdapterFactory;
import com.threatconnect.sdk.client.writer.DataStoreWriterAdapater;
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
        Connection conn = null;

        try {

            System.getProperties().setProperty("threatconnect.api.config", "/config.properties");
            conn = new Connection();

            create(conn);
            update(conn);
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
        DataStoreWriterAdapater writerAdapater = WriterAdapterFactory.createDataStoreWriterAdapter(conn);

        System.out.println(writerAdapater.createOrganization("example", "1", "System", "{\"name\": \"Fred\"}"));
    }

    public static void update(Connection conn)
    {
        DataStoreWriterAdapater writerAdapater = WriterAdapterFactory.createDataStoreWriterAdapter(conn);

        System.out.println(writerAdapater.updateOrganization("example", "1", "System", "{\"name\": \"Bob\"}"));
    }


    public static void get(Connection conn)
    {
        DataStoreReaderAdapter readerAdapter = ReaderAdapterFactory.createDataStoreReaderAdapter(conn);

        System.out.println(readerAdapter.getOrganization("example", "_search", "System"));
    }

    public static void delete(Connection conn)
    {
        DataStoreWriterAdapater writerAdapater = WriterAdapterFactory.createDataStoreWriterAdapter(conn);

        System.out.println(writerAdapater.deleteOrganization("example", "1", "System"));
    }
}
