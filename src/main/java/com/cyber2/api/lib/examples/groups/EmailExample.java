package com.cyber2.api.lib.examples.groups;

import com.cyber2.api.lib.client.reader.AbstractGroupReaderAdapter;
import com.cyber2.api.lib.client.reader.ReaderAdapterFactory;
import com.cyber2.api.lib.client.writer.AbstractGroupWriterAdapter;
import com.cyber2.api.lib.client.writer.WriterAdapterFactory;
import com.cyber2.api.lib.conn.Connection;
import com.cyber2.api.lib.exception.FailedResponseException;
import com.cyber2.api.lib.server.entity.Email;
import com.cyber2.api.lib.server.entity.Group;
import com.cyber2.api.lib.server.response.entity.ApiEntitySingleResponse;
import java.io.IOException;
import java.util.List;

public class EmailExample {

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
        AbstractGroupReaderAdapter<Email> reader = ReaderAdapterFactory.createEmailGroupReader(conn);
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
        AbstractGroupWriterAdapter<Email> writer = WriterAdapterFactory.createEmailGroupWriter(conn);

        Email email = createTestEmail();

        try {
            System.out.println("Before: " + email.toString());
            ApiEntitySingleResponse<Email, ?> response = writer.create(email);
            if (response.isSuccess()) {
                Email savedEmail = response.getItem();
                System.out.println("Saved: " + savedEmail.toString());
            } else {
                System.err.println("Error: " + response.getMessage());

            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static void doDelete(Connection conn) {
        AbstractGroupWriterAdapter<Email> writer = WriterAdapterFactory.createEmailGroupWriter(conn);

        Email email = createTestEmail();

        try {
            ApiEntitySingleResponse<Email, ?> createResponse = writer.create(email);
            if (createResponse.isSuccess()) {
                System.out.println("Saved: " + createResponse.getItem());
                ApiEntitySingleResponse<Email, ?> deleteResponse = writer.delete(createResponse.getItem().getId());
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
        AbstractGroupWriterAdapter<Email> writer = WriterAdapterFactory.createEmailGroupWriter(conn);

        Email email = createTestEmail();

        try {
            ApiEntitySingleResponse<Email, ?> createResponse = writer.create(email);
            if (createResponse.isSuccess()) {
                System.out.println("Created Email: " + createResponse.getItem());

                Email updatedEmail = createResponse.getItem();
                updatedEmail.setName("UPDATED: " + createResponse.getItem().getName());
                System.out.println("Saving Updated Email: " + updatedEmail);

                ApiEntitySingleResponse<Email, ?> updateResponse = writer.update(updatedEmail);
                if (updateResponse.isSuccess()) {
                    System.out.println("Updated Email: " + updateResponse.getItem());
                } else {
                    System.err.println("Failed to Update Email: " + updateResponse.getMessage());
                }
            } else {
                System.err.println("Failed to Create Email: " + createResponse.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static Email createTestEmail() {
        Email email = new Email();
        email.setName("Test Email");
        email.setOwnerName("System");
        email.setFrom("admin@test.com");
        email.setTo("test@test.com");
        email.setSubject("Test Subject");
        email.setBody("Test Body");
        email.setHeader("Test Header");

        return email;
    }

}
