package com.cyber2.api.lib.examples.groups;

import com.cyber2.api.lib.client.reader.AbstractGroupReaderAdapter;
import com.cyber2.api.lib.client.reader.ReaderAdapterFactory;
import com.cyber2.api.lib.client.writer.AbstractGroupWriterAdapter;
import com.cyber2.api.lib.client.writer.WriterAdapterFactory;
import com.cyber2.api.lib.conn.Connection;
import com.cyber2.api.lib.exception.FailedResponseException;
import com.cyber2.api.lib.server.entity.Signature;
import com.cyber2.api.lib.server.entity.Group;
import com.cyber2.api.lib.server.response.entity.ApiEntitySingleResponse;
import java.io.IOException;
import java.util.List;

public class SignatureExample {

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
        AbstractGroupReaderAdapter<Signature> reader = ReaderAdapterFactory.createSignatureGroupReader(conn);
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
        AbstractGroupWriterAdapter<Signature> writer = WriterAdapterFactory.createSignatureGroupWriter(conn);

        Signature signature = createTestSignature();

        try {
            System.out.println("Before: " + signature.toString());
            ApiEntitySingleResponse<Signature, ?> response = writer.create(signature);
            if (response.isSuccess()) {
                Signature savedSignature = response.getItem();
                System.out.println("Saved: " + savedSignature.toString());
            } else {
                System.err.println("Error: " + response.getMessage());

            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static void doDelete(Connection conn) {
        AbstractGroupWriterAdapter<Signature> writer = WriterAdapterFactory.createSignatureGroupWriter(conn);

        Signature signature = createTestSignature();

        try {
            ApiEntitySingleResponse<Signature, ?> createResponse = writer.create(signature);
            if (createResponse.isSuccess()) {
                System.out.println("Saved: " + createResponse.getItem());
                ApiEntitySingleResponse<Signature, ?> deleteResponse = writer.delete(createResponse.getItem().getId());
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
        AbstractGroupWriterAdapter<Signature> writer = WriterAdapterFactory.createSignatureGroupWriter(conn);

        Signature signature = createTestSignature();

        try {
            ApiEntitySingleResponse<Signature, ?> createResponse = writer.create(signature);
            if (createResponse.isSuccess()) {
                System.out.println("Created Signature: " + createResponse.getItem());

                Signature updatedSignature = createResponse.getItem();
                updatedSignature.setName("UPDATED: " + createResponse.getItem().getName());
                System.out.println("Saving Updated Signature: " + updatedSignature);

                ApiEntitySingleResponse<Signature, ?> updateResponse = writer.update(updatedSignature);
                if (updateResponse.isSuccess()) {
                    System.out.println("Updated Signature: " + updateResponse.getItem());
                } else {
                    System.err.println("Failed to Update Signature: " + updateResponse.getMessage());
                }
            } else {
                System.err.println("Failed to Create Signature: " + createResponse.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static Signature createTestSignature() {
        Signature signature = new Signature();
        signature.setName("Test Signature");
        signature.setOwnerName("System");
        signature.setFileName("test-file.txt");
        signature.setFileType("YARA");
        signature.setFileText("abcdefghijklmnopqrstuvwxyz");

        return signature;
    }

}
