package com.cyber2.api.lib.examples.groups;

import com.cyber2.api.lib.client.reader.AbstractGroupReaderAdapter;
import com.cyber2.api.lib.client.reader.ReaderAdapterFactory;
import com.cyber2.api.lib.client.writer.AbstractGroupWriterAdapter;
import com.cyber2.api.lib.client.writer.WriterAdapterFactory;
import com.cyber2.api.lib.conn.Connection;
import com.cyber2.api.lib.exception.FailedResponseException;
import com.cyber2.api.lib.server.entity.Adversary;
import com.cyber2.api.lib.server.entity.Group;
import com.cyber2.api.lib.server.response.entity.ApiEntitySingleResponse;
import java.io.IOException;
import java.util.List;

public class AdversaryExample {

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
        AbstractGroupReaderAdapter<Adversary> reader = ReaderAdapterFactory.createAdversaryGroupReader(conn);
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
        AbstractGroupWriterAdapter<Adversary> writer = WriterAdapterFactory.createAdversaryGroupWriter(conn);

        Adversary adversary = createTestAdversary();

        try {
            System.out.println("Before: " + adversary.toString());
            ApiEntitySingleResponse<Adversary, ?> response = writer.create(adversary);
            if (response.isSuccess()) {
                Adversary savedAdversary = response.getItem();
                System.out.println("Saved: " + savedAdversary.toString());
            } else {
                System.err.println("Error: " + response.getMessage());

            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static void doDelete(Connection conn) {
        AbstractGroupWriterAdapter<Adversary> writer = WriterAdapterFactory.createAdversaryGroupWriter(conn);

        Adversary adversary = createTestAdversary();

        try {
            ApiEntitySingleResponse<Adversary, ?> createResponse = writer.create(adversary);
            if (createResponse.isSuccess()) {
                System.out.println("Saved: " + createResponse.getItem());
                ApiEntitySingleResponse<Adversary, ?> deleteResponse = writer.delete(createResponse.getItem().getId());
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
        AbstractGroupWriterAdapter<Adversary> writer = WriterAdapterFactory.createAdversaryGroupWriter(conn);

        Adversary adversary = createTestAdversary();

        try {
            ApiEntitySingleResponse<Adversary, ?> createResponse = writer.create(adversary);
            if (createResponse.isSuccess()) {
                System.out.println("Created Adversary: " + createResponse.getItem());

                Adversary updatedAdversary = createResponse.getItem();
                updatedAdversary.setName("UPDATED: " + createResponse.getItem().getName());
                System.out.println("Saving Updated Adversary: " + updatedAdversary);

                ApiEntitySingleResponse<Adversary, ?> updateResponse = writer.update(updatedAdversary);
                if (updateResponse.isSuccess()) {
                    System.out.println("Updated Adversary: " + updateResponse.getItem());
                } else {
                    System.err.println("Failed to Update Adversary: " + updateResponse.getMessage());
                }
            } else {
                System.err.println("Failed to Create Adversary: " + createResponse.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static Adversary createTestAdversary() {
        Adversary adversary = new Adversary();
        adversary.setName("Test Adversary");
        adversary.setOwnerName("System");

        return adversary;
    }

}
