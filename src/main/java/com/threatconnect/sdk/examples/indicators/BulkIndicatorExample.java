package com.threatconnect.sdk.examples.indicators;

import com.threatconnect.sdk.client.reader.AbstractIndicatorReaderAdapter;
import com.threatconnect.sdk.client.reader.ReaderAdapterFactory;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.entity.Address;
import com.threatconnect.sdk.server.entity.Indicator;
import com.threatconnect.sdk.server.response.entity.BulkStatusResponse;

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
