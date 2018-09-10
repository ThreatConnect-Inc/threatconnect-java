package com.threatconnect.sdk.examples.groups;

import com.google.common.collect.Lists;
import com.threatconnect.sdk.app.SdkAppConfig;
import com.threatconnect.sdk.client.fluent.TaskBuilder;
import com.threatconnect.sdk.client.fluent.UserBuilder;
import com.threatconnect.sdk.client.reader.ReaderAdapterFactory;
import com.threatconnect.sdk.client.reader.TaskReaderAdapter;
import com.threatconnect.sdk.client.response.IterableResponse;
import com.threatconnect.sdk.client.writer.TaskWriterAdapter;
import com.threatconnect.sdk.client.writer.WriterAdapterFactory;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.entity.Group;
import com.threatconnect.sdk.server.entity.Task;
import com.threatconnect.sdk.server.entity.User;
import com.threatconnect.sdk.server.response.entity.ApiEntitySingleResponse;
import com.threatconnect.sdk.server.response.entity.UserResponse;
import com.threatconnect.sdk.server.response.entity.data.TaskResponseData;
import com.threatconnect.sdk.server.response.entity.data.UserResponseData;

import java.io.IOException;
import java.util.Date;

/**
 * Created by moweis-ad on 3/16/16.
 */
public class TaskExample {

    public static void main(String[] args) {

        Connection conn = null;

        try {
            System.getProperties().setProperty("threatconnect.api.config", "/config.properties");
            conn = new Connection(SdkAppConfig.getInstance());

            testCreateTask(conn);

            testGetTask(conn);

            testUpdateTask(conn);

            testUpdateTaskWithAssignee(conn);

            testDeleteTask(conn);

            testTaskAssignee(conn);

            testTaskEscalatee(conn);

        } catch (IOException ioe) {
            System.err.println("Error: " + ioe);
            ioe.printStackTrace();
        }
    }

    private static void testCreateTask(Connection conn) {
        TaskWriterAdapter writer = WriterAdapterFactory.createTaskWriter(conn);
        Task task = createTestTask();
        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create Task
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Task, ?> response = writer.create(task);

            if (response.isSuccess()) {
                System.out.println("Created Task: " + response.getItem() + "\n");
            } else {
                System.err.println("Could not create Task: " + response.getMessage() + "\n");
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString() + "\n");
        }
    }

    private static void testGetTask(Connection conn) throws IOException {
        TaskReaderAdapter reader = ReaderAdapterFactory.createTaskReader(conn);
        IterableResponse<Task> data;
        try {
            // -----------------------------------------------------------------------------------------------------------
            // Get All Tasks
            // -----------------------------------------------------------------------------------------------------------
            data = reader.getAll();
            for (Group g : data) {
                System.out.println("Task found: " + g);
            }
            System.out.println();
        } catch (FailedResponseException ex) {
            System.err.println("Error: " + ex + "\n");
        }
    }

    private static void testUpdateTask(Connection conn) throws IOException {
        TaskWriterAdapter writer = WriterAdapterFactory.createTaskWriter(conn);
        TaskReaderAdapter reader = ReaderAdapterFactory.createTaskReader(conn);

        Task task = createTestTask();
        try {
            // Create task
            ApiEntitySingleResponse<Task, ?> createResponse = writer.create(task);
            if (!createResponse.isSuccess()) {
                System.err.println("Create task failed: " + createResponse.getMessage());
                return;
            }
            Task updatableTask = createResponse.getItem();
            System.out.println("Created task: " + updatableTask + "\nwith Id: " + updatableTask.getId());
            Integer id = updatableTask.getId();

            // Update task
            updatableTask.setName("Power lifting");
            ApiEntitySingleResponse<Task, ?> updateResponse = writer.update(updatableTask);
            if (!updateResponse.isSuccess()) {
                System.err.println("Update task failed: " + updateResponse.getMessage());
                return;
            }
            Task updatedTask = updateResponse.getItem();
            System.out.println("Updated task: " + updatedTask);

            // Retrieve task again and check changes are there
            Task retrievedTask = reader.getById(id);
            if (retrievedTask == null) {
                System.err.println("Failed to retrieve updated task: " + id);
                return;
            }

            // Now check if the update succeeded
            if (!String.valueOf(retrievedTask.getName()).equals(updatedTask.getName())) {
                // If the name is the same, the update failed
                System.err.println("Failed to update task; changes were not saved");
                return;
            } else {
                System.out.println("Updated task successfully: " + retrievedTask);
            }

            // Now let's delete it (not a big deal if it fails)
            ApiEntitySingleResponse<Task, ?> deleteResponse = writer.delete(id);
            System.out.println("Deleted updated task " + (deleteResponse.isSuccess()
                    ? "successfully\n"
                    : "unsuccessfully: " + deleteResponse.getMessage() + "\n"));
        } catch (IOException | FailedResponseException e) {
            System.err.println("Error: " + e.toString() + "\n");
        }
    }

    // The User should not be updated along with the Task
    private static void testUpdateTaskWithAssignee(Connection conn) {
        TaskWriterAdapter writer = WriterAdapterFactory.createTaskWriter(conn);
        TaskReaderAdapter reader = ReaderAdapterFactory.createTaskReader(conn);

        Task task = createTestTask();
        User assignee = createTestUser();
        try {
            // Create task
            ApiEntitySingleResponse<Task, ?> createResponse = writer.create(task);
            if (!createResponse.isSuccess()) {
                System.err.println("Create task failed: " + createResponse.getMessage());
                return;
            }
            Task updatableTask = createResponse.getItem();
            System.out.println("Created task: " + updatableTask + "\nwith Id: " + updatableTask.getId());
            Integer id = updatableTask.getId();

            // Add assignee
            UserResponse userResp = writer.createAssignee(id, assignee);
            if (userResp.isSuccess()) {
                System.out.println("Created Assignee: " + assignee);
            } else {
                System.err.println("Create failed: " + userResp.getMessage());
            }

            // Update task
            updatableTask.setName("Power lifting");
            assignee.setRole("Administrator");
            updatableTask.setAssignee(Lists.newArrayList(assignee));
            ApiEntitySingleResponse<Task, ?> updateResponse = writer.update(updatableTask);
            if (!updateResponse.isSuccess()) {
                System.err.println("Update task failed: " + updateResponse.getMessage());
                return;
            }
            Task updatedTask = updateResponse.getItem();
            System.out.println("Updated task: " + updatedTask);

            // Retrieve task again and check changes are there
            Task retrievedTask = reader.getById(id);
            if (retrievedTask == null) {
                System.err.println("Failed to retrieve updated task: " + id);
                return;
            }

            // Now check if the update succeeded
            if (!String.valueOf(retrievedTask.getName()).equals(updatedTask.getName())) {
                // If the name is the same, the update failed
                System.err.println("Failed to update task; changes were not saved");
                return;
            } else {
                System.out.println("Updated task successfully: " + retrievedTask);
            }

            // Now let's delete it (not a big deal if it fails)
            ApiEntitySingleResponse<Task, ?> deleteResponse = writer.delete(id);
            System.out.println("Deleted updated task " + (deleteResponse.isSuccess()
                    ? "successfully\n"
                    : "unsuccessfully: " + deleteResponse.getMessage() + "\n"));
        } catch (IOException | FailedResponseException e) {
            System.err.println("Error: " + e.toString() + "\n");
        }
    }

    private static void testDeleteTask(Connection conn) {
        TaskWriterAdapter writer = WriterAdapterFactory.createTaskWriter(conn);
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
                    System.out.println("Deleted: " + createResponse.getItem() + "\n");
                } else {
                    System.err.println("Delete Failed. Cause: " + deleteResponse.getMessage() + "\n");
                }
            } else {
                System.err.println("Create Failed. Cause: " + createResponse.getMessage() + "\n");
            }
        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString() + "\n");
        }

    }

    private static void testTaskAssignee(Connection conn) {
        TaskWriterAdapter writer = WriterAdapterFactory.createTaskWriter(conn);
        TaskReaderAdapter reader = ReaderAdapterFactory.createTaskReader(conn);

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
                System.out.println("--- Assignee Deleted ---\nUser id: " + id + "\n");
            } else {
                System.err.println("User not deleted: " + deleteResponse.getMessage() + "\n");
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString() + "\n");
        }
    }

    private static void testTaskEscalatee(Connection conn) {
        TaskWriterAdapter writer = WriterAdapterFactory.createTaskWriter(conn);
        TaskReaderAdapter reader = ReaderAdapterFactory.createTaskReader(conn);

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
                System.out.println("--- Escalatee Deleted ---\nUser id: " + id + "\n");
            } else {
                System.err.println("User not deleted: " + deleteResponse.getMessage() + "\n");
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString() + "\n");
        }
    }


    private static Task createTestTask() {
        // God I hate the Date class
        Date later = new Date();
        later.setTime(new Date().getTime() + 10000);
        return new TaskBuilder()
                .withName("Rock Lifting")
                .withStatus("In Progress")
                .withOwnerName("System")
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
