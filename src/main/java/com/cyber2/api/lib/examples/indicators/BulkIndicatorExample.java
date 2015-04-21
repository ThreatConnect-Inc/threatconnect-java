package com.cyber2.api.lib.examples.indicators;

import com.cyber2.api.lib.client.reader.AbstractIndicatorReaderAdapter;
import com.cyber2.api.lib.client.reader.ReaderAdapterFactory;
import com.cyber2.api.lib.conn.Connection;
import com.cyber2.api.lib.exception.FailedResponseException;
import com.cyber2.api.lib.server.entity.Address;
import com.cyber2.api.lib.server.entity.Indicator;
import com.cyber2.api.lib.server.response.entity.BulkStatusResponse;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Created by cblades on 4/21/2015.
 */
public class BulkIndicatorExample
{
    public static void main(String[] args) {
        System.getProperties().setProperty("threatconnect.api.config", "/config.properties");
        try (Connection conn = new Connection()){
            doGetStatus(conn);
            doDownloadJson(conn);
        } catch (IOException ex ) {
            System.err.println("Error: " + ex);
        }
    }

    private static void doGetStatus(Connection conn)
    {
        AbstractIndicatorReaderAdapter<Address> reader =
                ReaderAdapterFactory.getIndicatorReader(Indicator.Type.Address, conn);

        try
        {
            BulkStatusResponse response = reader.getBulkStatus("foobar");
            if (response.isSuccess()) {
                System.out.println(response.getItem().getNextRun());
            }
        } catch (FailedResponseException  | IOException e)
        {
            System.err.println(e.getMessage());
        }
    }

    private static void doDownloadJson(Connection conn)
    {
        AbstractIndicatorReaderAdapter<Address> reader =
                ReaderAdapterFactory.getIndicatorReader(Indicator.Type.Address, conn);

        try
        {
            reader.downloadBulkIndicatorJson("foobar", Paths.get("./foobarBulk.json"));

            Scanner in = new Scanner(new FileInputStream("./foobarBulk.json"));
            while (in.hasNextLine()) {
                System.out.println(in.nextLine());
            }
        } catch (IOException e)
        {
            System.err.println(e.getMessage());
        }
    }
}
