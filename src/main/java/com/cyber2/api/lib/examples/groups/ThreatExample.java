package com.cyber2.api.lib.examples.groups;

import com.cyber2.api.lib.client.reader.AbstractGroupReaderAdapter;
import com.cyber2.api.lib.client.reader.ReaderAdapterFactory;
import com.cyber2.api.lib.client.writer.AbstractGroupWriterAdapter;
import com.cyber2.api.lib.client.writer.WriterAdapterFactory;
import com.cyber2.api.lib.conn.Connection;
import com.cyber2.api.lib.exception.FailedResponseException;
import com.cyber2.api.lib.server.entity.Threat;
import com.cyber2.api.lib.server.entity.Group;
import com.cyber2.api.lib.server.response.entity.ApiEntitySingleResponse;
import java.io.IOException;
import java.util.List;

public class ThreatExample {

    public static void main(String[] args) {

        Connection conn = null;

        try {

            System.getProperties().setProperty("threatconnect.api.config", "/config.properties");
            conn = new Connection();

            doGet(conn);

            doCreate(conn);

            doUpdate(conn);

            doDelete(conn);

        } catch (IOException ex ) {
            System.err.println("Error: " + ex);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    private static void doGet(Connection conn) throws IOException {
        AbstractGroupReaderAdapter<Threat> reader = ReaderAdapterFactory.createThreatGroupReader(conn);
        List<Group> data;
        try {
            data = reader.getGroups();
            for (Group g : data) {
                System.out.println("Group: " + g);
            }
        } catch (FailedResponseException ex) {
            System.err.println("Error: " + ex);
        }
    }

    private static void doCreate(Connection conn) {
        AbstractGroupWriterAdapter<Threat> writer = WriterAdapterFactory.createThreatGroupWriter(conn);

        Threat threat = createTestThreat();

        try {
            System.out.println("Before: " + threat.toString());
            ApiEntitySingleResponse<Threat, ?> response = writer.create(threat);
            if (response.isSuccess()) {
                Threat savedThreat = response.getItem();
                System.out.println("Saved: " + savedThreat.toString());
            } else {
                System.err.println("Error: " + response.getMessage());

            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static void doDelete(Connection conn) {
        AbstractGroupWriterAdapter<Threat> writer = WriterAdapterFactory.createThreatGroupWriter(conn);

        Threat threat = createTestThreat();

        try {
            ApiEntitySingleResponse<Threat, ?> createResponse = writer.create(threat);
            if (createResponse.isSuccess()) {
                System.out.println("Saved: " + createResponse.getItem());
                ApiEntitySingleResponse<Threat, ?> deleteResponse = writer.delete(createResponse.getItem().getId());
                if (deleteResponse.isSuccess()) {
                    System.out.println("Deleted: " + createResponse.getItem());
                } else {
                    System.err.println("Delete Failed. Cause: " + deleteResponse.getMessage());
                }
            } else {
                System.err.println("Create Failed. Cause: " + createResponse.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static void doUpdate(Connection conn) {
        AbstractGroupWriterAdapter<Threat> writer = WriterAdapterFactory.createThreatGroupWriter(conn);

        Threat threat = createTestThreat();

        try {
            ApiEntitySingleResponse<Threat, ?> createResponse = writer.create(threat);
            if (createResponse.isSuccess()) {
                System.out.println("Created Threat: " + createResponse.getItem());

                Threat updatedThreat = createResponse.getItem();
                updatedThreat.setName("UPDATED: " + createResponse.getItem().getName());
                System.out.println("Saving Updated Threat: " + updatedThreat);

                ApiEntitySingleResponse<Threat, ?> updateResponse = writer.update(updatedThreat);
                if (updateResponse.isSuccess()) {
                    System.out.println("Updated Threat: " + updateResponse.getItem());
                } else {
                    System.err.println("Failed to Update Threat: " + updateResponse.getMessage());
                }
            } else {
                System.err.println("Failed to Create Threat: " + createResponse.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static Threat createTestThreat() {
        Threat threat = new Threat();
        threat.setName("Test Threat");
        threat.setOwnerName("System");

        return threat;
    }

}
