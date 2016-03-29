package com.threatconnect.sdk.examples.groups;

import com.threatconnect.sdk.client.fluent.TaskBuilder;
import com.threatconnect.sdk.client.fluent.UserBuilder;
import com.threatconnect.sdk.client.reader.ReaderAdapterFactory;
import com.threatconnect.sdk.client.reader.TaskReaderAdapter;
import com.threatconnect.sdk.client.response.IterableResponse;
import com.threatconnect.sdk.client.writer.TaskWriterAdapter;
import com.threatconnect.sdk.client.writer.WriterAdapterFactory;
import com.threatconnect.sdk.config.Configuration;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.entity.Group;
import com.threatconnect.sdk.server.entity.Task;
import com.threatconnect.sdk.server.entity.User;
import com.threatconnect.sdk.server.response.entity.ApiEntitySingleResponse;
import com.threatconnect.sdk.server.response.entity.TaskResponse;
import com.threatconnect.sdk.server.response.entity.UserResponse;
import com.threatconnect.sdk.server.response.entity.data.TaskResponseData;
import com.threatconnect.sdk.server.response.entity.data.UserResponseData;

import java.io.IOException;
import java.io.Reader;
import java.util.Date;

/**
 * Created by moweis-ad on 3/16/16.
 */
public class TaskExample {

    public static void main(String[] args) {

        Connection conn = null;

        try {
            //System.getProperties().setProperty("threatconnect.api.config", "/config.properties");
            //conn = new Connection();

            Configuration config = new Configuration("https://localhost:8443/api",
                    "70621724379179329486",
                    "dttcE9gsrmU@e6d^SHJra8ufVcLD^bJC^@@J9eESztqX8Q8ioCd2lPYAkI2lNxwH",
                    "System");
            conn = new Connection(config);

            doCreate(conn);

            doGet(conn);

            doDelete(conn);

            doCreateAndGetAssignee(conn);

            doCreateAndGetEscalatee(conn);

            //doCreateAssignee(conn);

            //doGetAssignee(conn);

            //doGetAssignees(conn);


        } catch (IOException ioe) {
            System.err.println("Error: " + ioe);
            ioe.printStackTrace();
        } finally {
            if (conn != null)
                conn.disconnect();
        }
    }

    private static void doCreate(Connection conn) {
        TaskWriterAdapter writer = WriterAdapterFactory.createTaskGroupWriter(conn);
        Task task = createTestTask();
        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create Task
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Task, ?> response = writer.create(task);

            if (response.isSuccess()) {
                System.out.println("Created Task: " + response.getItem());
            } else {
                System.err.println("Could not create Task: " + response.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }
    }

    private static void doGet(Connection conn) throws IOException {
        TaskReaderAdapter reader = ReaderAdapterFactory.createTaskGroupReader(conn);
        IterableResponse<Task> data;
        try {
            // -----------------------------------------------------------------------------------------------------------
            // Get All Tasks
            // -----------------------------------------------------------------------------------------------------------
            data = reader.getAll();
            for (Group g : data) {
                System.out.println("Task found: " + g);
            }
        } catch (FailedResponseException ex) {
            System.err.println("Error: " + ex);
        }
    }



    private static void doDelete(Connection conn) {
        TaskWriterAdapter writer = WriterAdapterFactory.createTaskGroupWriter(conn);
        Task task = createTestTask();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Update Task
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Task, ?> createResponse = writer.create(task);
            if (createResponse.isSuccess()) {
                System.out.println("Created Task: " + createResponse.getItem());
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

    private static void doCreateAndGetAssignee(Connection conn) {
        TaskWriterAdapter writer = WriterAdapterFactory.createTaskGroupWriter(conn);
        TaskReaderAdapter reader = ReaderAdapterFactory.createTaskGroupReader(conn);

        Task task = createTestTask();
        User assignee = createTestUser();

        try {
            // Create task
            ApiEntitySingleResponse<Task, TaskResponseData> taskResp = writer.create(task);
            if (!taskResp.isSuccess()) {
                System.err.println("Failed to create Task during Assignee test: " + task);
                return;
            }

            // Create assignee
            Integer id = ((Task)taskResp.getData().getData()).getId();
            UserResponse userResp = writer.createAssignee(id, assignee);
            if (userResp.isSuccess()) {
                System.out.println("Created Assignee: " + assignee);
            } else {
                System.err.println("Create failed: " + userResp.getMessage());
            }

            // Get all assignees
            IterableResponse<User> getAllResponse = reader.getAssignees(id);
            if (getAllResponse != null) {
                System.out.println("--- Assignees found ---");
                for (User user : getAllResponse)
                    System.out.println("User: " + user);
            } else {
                System.err.println("Assignees not found for id: " + id);
            }

            // Get individual assignee
            UserResponse getResponse = reader.getAssignee(id, assignee.getUserName());
            if (getResponse.isSuccess()) {
                System.out.println("--- Individual Assignee found ---\nUser: " + getResponse.getItem());
            } else {
                System.err.println("User not found: " + getResponse.getMessage());
            }

            // And delete it...
            UserResponse deleteResponse = writer.deleteAssignee(id, getResponse.getItem().getUserName());
            if (deleteResponse.isSuccess()) {
                System.out.println("--- Assignee Deleted ---\nUser id: " + id);
            } else {
                System.err.println("User not deleted: " + deleteResponse.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }
    }

    private static void doCreateAndGetEscalatee(Connection conn) {
        TaskWriterAdapter writer = WriterAdapterFactory.createTaskGroupWriter(conn);
        TaskReaderAdapter reader = ReaderAdapterFactory.createTaskGroupReader(conn);

        Task task = createTestTask();
        User escalatee = createAdminUser();

        try {
            // Create task
            ApiEntitySingleResponse<Task, TaskResponseData> taskResp = writer.create(task);
            if (!taskResp.isSuccess()) {
                System.err.println("Failed to create Task during Escalatee test: " + task);
                return;
            }

            // Create escalatee
            Integer id = ((Task)taskResp.getData().getData()).getId();
            ApiEntitySingleResponse<User, UserResponseData> userResp = writer.createEscalatee(id, escalatee);
            if (userResp.isSuccess()) {
                System.out.println("Created Escalatee: " + escalatee);
            } else {
                System.err.println("Create failed: " + userResp.getMessage());
            }

            // Get all escalatees
            IterableResponse<User> escalatees = reader.getEscalatees(id);
            if (escalatees != null) {
                System.out.println("--- Escalatees found ---");
                for (User user : escalatees)
                    System.out.println("User: " + user);
            } else {
                System.err.println("Escalatees not found for id: " + id);
            }

            // Get individual escalatee
            UserResponse getResponse = reader.getEscalatee(id, escalatee.getUserName());
            if (getResponse.isSuccess()) {
                System.out.println("--- Individual Escalatee found ---\nUser: " + getResponse.getItem());
            } else {
                System.err.println("User not found: " + getResponse.getMessage());
            }

            // And delete it...
            UserResponse deleteResponse = writer.deleteEscalatee(id, getResponse.getItem().getUserName());
            if (deleteResponse.isSuccess()) {
                System.out.println("--- Escalatee Deleted ---\nUser id: " + id);
            } else {
                System.err.println("User not deleted: " + deleteResponse.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }
    }


    private static Task createTestTask() {
        // God I hate the Date class
        Date later = new Date();
        later.setTime(new Date().getTime() + 10000);
        return new TaskBuilder()
                .withName("Rock Lifting")
                .withStatus("In Progress")
                .withDueDate(later)
                .withEscalationDate(later)
                .withReminderDate(later)
                .createTask();
    }

    private static User createTestUser() {
        return new UserBuilder()
                .withUserName("a@b.com")
                .withRole("User")
                .createUser();
    }

    private static User createAdminUser() {
        return new UserBuilder()
                .withUserName("admin@b.com")
                .withRole("Administrator")
                .createUser();
    }
}
