package com.threatconnect.sdk.examples.groups;

import com.threatconnect.sdk.client.reader.AbstractGroupReaderAdapter;
import com.threatconnect.sdk.client.reader.ReaderAdapterFactory;
import com.threatconnect.sdk.client.response.IterableResponse;
import com.threatconnect.sdk.client.writer.AbstractGroupWriterAdapter;
import com.threatconnect.sdk.client.writer.WriterAdapterFactory;
import com.threatconnect.sdk.config.Configuration;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.entity.Group;
import com.threatconnect.sdk.server.entity.Task;
import com.threatconnect.sdk.server.response.entity.ApiEntitySingleResponse;

import java.io.IOException;

/**
 * Created by moweis-ad on 3/16/16.
 */
public class TaskExample {

    public static void main(String[] args) {

        Connection conn = null;

        try {
            System.getProperties().setProperty("threatconnect.api.config", "/config.properties");
            conn = new Connection();

            doCreate(conn);

            doGet(conn);

            doDelete(conn);

        } catch (IOException ioe) {
            System.err.println("Error: " + ioe);
            ioe.printStackTrace();
        } finally {
            if (conn != null)
                conn.disconnect();
        }
    }

    private static void doGet(Connection conn) throws IOException {

        AbstractGroupReaderAdapter<Task> reader = ReaderAdapterFactory.createTaskGroupReader(conn);
        IterableResponse<Task> data;
        try {
            // -----------------------------------------------------------------------------------------------------------
            // Get Tasks
            // -----------------------------------------------------------------------------------------------------------
            data = reader.getAll();
            for (Group g : data) {
                System.out.println("Task: " + g);
            }
        } catch (FailedResponseException ex) {
            System.err.println("Error: " + ex);
        }
    }

    private static void doCreate(Connection conn) {
        AbstractGroupWriterAdapter<Task> writer = WriterAdapterFactory.createTaskGroupWriter(conn);

        Task threat = createTestTask();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create Task
            // -----------------------------------------------------------------------------------------------------------
            System.out.println("Before: " + threat.toString());
            ApiEntitySingleResponse<Task, ?> response = writer.create(threat);

            if (response.isSuccess()) {
                Task savedThreat = response.getItem();
                System.out.println("Saved: " + savedThreat.toString());

            } else {
                System.err.println("Error: " + response.getMessage());

            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static void doDelete(Connection conn) {
        AbstractGroupWriterAdapter<Task> writer = WriterAdapterFactory.createTaskGroupWriter(conn);

        Task threat = createTestTask();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Update Task
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Task, ?> createResponse = writer.create(threat);
            if (createResponse.isSuccess()) {
                System.out.println("Saved: " + createResponse.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Delete Task
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse<Task, ?> deleteResponse = writer.delete(createResponse.getItem().getId());
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

    private static Task createTestTask() {
        Task task = new Task();
        task.setName("Test Task");
        task.setOwnerName("System");
        return task;
    }
}
