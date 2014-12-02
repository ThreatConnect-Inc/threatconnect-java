package com.cyber2.api.lib.examples.groups;

import com.cyber2.api.lib.client.reader.AbstractGroupReaderAdapter;
import com.cyber2.api.lib.client.reader.ReaderAdapterFactory;
import com.cyber2.api.lib.client.writer.AbstractGroupWriterAdapter;
import com.cyber2.api.lib.client.writer.WriterAdapterFactory;
import com.cyber2.api.lib.conn.Connection;
import com.cyber2.api.lib.exception.FailedResponseException;
import com.cyber2.api.lib.server.entity.Incident;
import com.cyber2.api.lib.server.entity.Group;
import com.cyber2.api.lib.server.response.entity.ApiEntitySingleResponse;
import java.io.IOException;
import java.util.List;

public class IncidentExample {

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
        AbstractGroupReaderAdapter<Incident> reader = ReaderAdapterFactory.createIncidentGroupReader(conn);
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
        AbstractGroupWriterAdapter<Incident> writer = WriterAdapterFactory.createIncidentGroupWriter(conn);

        Incident incident = new Incident();
        incident.setName("Test Incident");
        incident.setOwnerName("System");

        try {
            System.out.println("Before: " + incident.toString());
            ApiEntitySingleResponse<Incident, ?> response = writer.create(incident);
            if (response.isSuccess()) {
                Incident savedIncident = response.getItem();
                System.out.println("Saved: " + savedIncident.toString());
            } else {
                System.err.println("Error: " + response.getMessage());

            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static void doDelete(Connection conn) {
        AbstractGroupWriterAdapter<Incident> writer = WriterAdapterFactory.createIncidentGroupWriter(conn);

        Incident incident = new Incident();
        incident.setName("Test Incident");
        incident.setOwnerName("System");

        try {
            ApiEntitySingleResponse<Incident, ?> createResponse = writer.create(incident);
            if (createResponse.isSuccess()) {
                System.out.println("Saved: " + createResponse.getItem());
                ApiEntitySingleResponse<Incident, ?> deleteResponse = writer.delete(createResponse.getItem().getId());
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
        AbstractGroupWriterAdapter<Incident> writer = WriterAdapterFactory.createIncidentGroupWriter(conn);

        Incident incident = new Incident();
        incident.setName("Test Incident");
        incident.setOwnerName("System");

        try {
            ApiEntitySingleResponse<Incident, ?> createResponse = writer.create(incident);
            if (createResponse.isSuccess()) {
                System.out.println("Created Incident: " + createResponse.getItem());

                Incident updatedIncident = createResponse.getItem();
                updatedIncident.setName("UPDATED: " + createResponse.getItem().getName());
                System.out.println("Saving Updated Incident: " + updatedIncident);

                ApiEntitySingleResponse<Incident, ?> updateResponse = writer.update(updatedIncident);
                if (updateResponse.isSuccess()) {
                    System.out.println("Updated Incident: " + updateResponse.getItem());
                } else {
                    System.err.println("Failed to Update Incident: " + updateResponse.getMessage());
                }
            } else {
                System.err.println("Failed to Create Incident: " + createResponse.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

}
